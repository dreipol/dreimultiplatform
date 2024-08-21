//
//  ObservableStore.swift
//  Multiplatform Redux Sample
//
//  Created by Samuel Bichsel on 30.10.20.
//  Copyright © 2020 dreipol GmbH. All rights reserved.
//

import Foundation

public final class ObservableStore: ObservableObject {
    let store: TypedStore

    public init(store: TypedStore) {
        self.store = store
    }
}
