package ch.dreipol.dreimultiplatform

import android.app.Activity
import com.google.android.play.core.review.ReviewManagerFactory

actual class PlatformFeatures {

    private lateinit var activity: Activity

    actual fun showRatingDialog() {
        val manager = ReviewManagerFactory.create(activity)
        val request = manager.requestReviewFlow()
        request.addOnCompleteListener {
            manager.launchReviewFlow(activity, it.result)
        }
    }

    fun init(activity: Activity) {
        this.activity = activity
    }

}