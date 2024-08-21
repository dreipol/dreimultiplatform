//
//  ReduxState.swift
//  Barryvox
//
//  Created by Laila Becker on 01.11.22.
//  Copyright © 2022 dreipol GmbH. All rights reserved.
//

import SwiftUI

public protocol ReduxGetter {
    associatedtype Value: Equatable
    associatedtype SwiftValue

    var mapper: (ApplicationState) -> Value { get }
    var swiftMapper: (Value) -> SwiftValue { get }
}

public struct ReduxMapper<Value: Equatable, SwiftValue>: ReduxGetter {
    public var mapper: (ApplicationState) -> Value
    public var swiftMapper: (Value) -> SwiftValue
    fileprivate var action: (_ dispatch: Dispatcher, _ state: ApplicationState, _ newValue: SwiftValue) -> Void

    public init(mapper: @escaping (ApplicationState) -> Value,
                swiftMapper: @escaping (Value) -> SwiftValue,
                action: @escaping (_: Dispatcher, _: ApplicationState, _: SwiftValue) -> Void) {
        self.mapper = mapper
        self.swiftMapper = swiftMapper
        self.action = action
    }
}

public extension ReduxMapper where Value == SwiftValue {
    init(mapper: @escaping (ApplicationState) -> Value, action: @escaping (_: Dispatcher, _: ApplicationState, _: Value) -> Void) {
        self.init(mapper: mapper, swiftMapper: { $0 }, action: action)
    }
}

public struct ReduxStateGetter<Value: Equatable, SwiftValue>: ReduxGetter {
    public var mapper: (ApplicationState) -> Value
    public var swiftMapper: (Value) -> SwiftValue
}

public extension ReduxStateGetter where Value == SwiftValue {
    init(mapper: @escaping (ApplicationState) -> Value) {
        self.init(mapper: mapper, swiftMapper: { $0 })
    }
}

// MARK: - Property wrappers

@propertyWrapper public struct ReduxState<Value: Equatable, SwiftValue>: SubscribedProperty {
    typealias Getter = ReduxMapper<Value, SwiftValue>

    private var mapper: ReduxMapper<Value, SwiftValue>
    fileprivate var getter: ReduxMapper<Value, SwiftValue> { mapper }

    @EnvironmentObject fileprivate var observableStore: ObservableStore
    @Dispatch private var dispatch: Dispatcher

    @State fileprivate var currentValue: Value?
    fileprivate let subscriptionHolder: SubscriptionHolder = .init()

    public init(_ mapper: ReduxMapper<Value, SwiftValue>) {
        self.mapper = mapper
    }

    public var wrappedValue: SwiftValue {
        get {
            mapper.swiftMapper(currentValue ?? mapper.mapper(observableStore.store.applicationState))
        }
        nonmutating set {
            mapper.action(dispatch, observableStore.store.applicationState, newValue)
        }
    }

    public var projectedValue: Binding<SwiftValue> {
        Binding {
            wrappedValue
        } set: { newValue in
            wrappedValue = newValue
        }
    }
}

@propertyWrapper public struct GetReduxState<Getter: ReduxGetter>: SubscribedProperty {
    fileprivate var getter: Getter

    @EnvironmentObject fileprivate var observableStore: ObservableStore

    @State fileprivate var currentValue: Getter.Value?
    fileprivate let subscriptionHolder: SubscriptionHolder = .init()

    public init(_ getter: Getter) {
        self.getter = getter
    }

    public var wrappedValue: Getter.SwiftValue {
        getter.swiftMapper(currentValue ?? getter.mapper(observableStore.store.applicationState))
    }
}

private class SubscriptionHolder {
    var subscription: (() -> KotlinUnit)?

    func subscribe(to store: TypedStore, receive: @escaping (ApplicationState) -> Void) {
        guard subscription == nil else {
            return
        }

        subscription = store.subscribe {
            receive(store.applicationState)
            return KotlinUnit()
        }
    }

    deinit {
        _ = subscription?()
    }
}

private protocol SubscribedProperty: DynamicProperty {
    associatedtype Getter: ReduxGetter

    var getter: Getter { get }
    var observableStore: ObservableStore { get }
    var currentValue: Getter.Value? { get nonmutating set }
    var subscriptionHolder: SubscriptionHolder { get }
}

private extension SubscribedProperty {
    public func update() {
        subscriptionHolder.subscribe(to: observableStore.store) { newState in
            let newValue = getter.mapper(newState)
            if newValue != currentValue {
                currentValue = newValue
            }
        }
    }
}
