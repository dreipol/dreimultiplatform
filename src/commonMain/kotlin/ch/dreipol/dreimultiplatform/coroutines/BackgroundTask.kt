package ch.dreipol.dreimultiplatform.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

/** A task that continues running while the app is in the background on iOS.
 */
expect fun CoroutineScope.launchBackgroundTask(block: suspend CoroutineScope.() -> Unit) : Job