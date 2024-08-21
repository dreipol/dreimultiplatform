//
//  Dispatch.swift
//  Barryvox
//
//  Created by Laila Becker on 01.11.22.
//  Copyright © 2022 dreipol GmbH. All rights reserved.
//

import SwiftUI

public struct Dispatcher {
    public typealias Thunk = ((@escaping (Any) -> Any, @escaping () -> ApplicationState, Any?) -> Any)

    private let dispatch: (Any) -> Void

    fileprivate init(dispatch: @escaping (Any) -> Void) {
        self.dispatch = dispatch
    }

    public func callAsFunction(_ action: Any) {
        dispatch(action)
    }

    public func callAsFunction(_ thunk: @escaping Thunk) {
        dispatch(ThunksKt.createThunkAction(thunk: thunk))
    }
}

@propertyWrapper public struct Dispatch: DynamicProperty {
    @EnvironmentObject private var observableStore: ObservableStore

    public init() {}

    public var wrappedValue: Dispatcher {
        return Dispatcher { action in
            _ = observableStore.store.dispatch(action)
        }
    }
}
