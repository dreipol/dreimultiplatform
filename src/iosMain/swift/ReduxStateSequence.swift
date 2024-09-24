//
//  ReduxStateSequence.swift
//  dreimultiplatform
//
//  Created by Laila Becker on 19.06.2024.
//  Copyright © 2024 dreipol GmbH. All rights reserved.
//

import SwiftUI

@propertyWrapper
public struct ReduxStateSequence<Getter: ReduxGetter>: DynamicProperty {
    nonisolated private let getter: Getter

    @Environment(\.reduxStore) private var store

    public init(_ getter: Getter) {
        self.getter = getter
    }

    // TODO: prefer this version once iOS 17 support is dropped
//    @available(iOS 18, *)
//    var wrappedValue: some AsyncSequence<Getter.SwiftValue, Never> {
    public var wrappedValue: AsyncMapSequence<SkieSwiftOptionalFlow<Any>, Getter.SwiftValue?> {
        store
            .flowOf {
                // swiftlint:disable:next force_cast
                let state = $0 as! ApplicationState
                return getter.mapper(state)
            }
            .map {
                guard let kotlinValue = $0 as? Getter.Value else {
                    return nil
                }

                return getter.swiftMapper(kotlinValue)
            }
    }
}
