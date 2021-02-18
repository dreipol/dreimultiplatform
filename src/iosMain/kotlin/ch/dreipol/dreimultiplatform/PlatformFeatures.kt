package ch.dreipol.dreimultiplatform

import platform.StoreKit.SKStoreReviewController

actual class PlatformFeatures {

    actual fun showRatingDialog() {
        SKStoreReviewController.requestReview()
    }
}