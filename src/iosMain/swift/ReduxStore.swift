//
//  Dispatch.swift
//  dreimultiplatform
//
//  Created by Laila Becker on 24.09.24.
//  Copyright © 2024 dreipol GmbH. All rights reserved.
//

import SwiftUI
import shared

private struct ReduxStore: EnvironmentKey {
    // ⚠️ You need to create this function in your project.
    static let previewStore = ApplicationStoreKt.createPreviewStore()

    static var defaultValue: TypedStore {
        guard ProcessInfo.processInfo.environment["XCODE_RUNNING_FOR_PREVIEWS"] != "1" else {
            return previewStore
        }

        return getAppConfiguration().store
    }
}

public extension EnvironmentValues {
    var reduxStore: TypedStore {
        get { self[ReduxStore.self] }
        set { self[ReduxStore.self] = newValue }
    }
}
