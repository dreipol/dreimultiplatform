package ch.dreipol.dreimultiplatform.coroutines

import co.touchlab.kermit.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import platform.UIKit.UIApplication

actual fun CoroutineScope.launchBackgroundTask(block: suspend CoroutineScope.() -> Unit): Job = BackgroundTask(launch(block = block))

private class BackgroundTask(val underlyingJob: Job) : Job by underlyingJob {
    private val backgroundTaskIdentifier =
        UIApplication.sharedApplication.beginBackgroundTaskWithExpirationHandler {
            Logger.w { "Cancelling long running background task!" }
            underlyingJob.cancel("Background Task is about to expire!")
        }

    @OptIn(InternalCoroutinesApi::class)
    private val completionHandle =
        underlyingJob.invokeOnCompletion(onCancelling = true) {
            UIApplication.sharedApplication.endBackgroundTask(backgroundTaskIdentifier)
        }
}