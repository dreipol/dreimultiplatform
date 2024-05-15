package ch.dreipol.dreimultiplatform.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

actual fun CoroutineScope.launchBackgroundTask(block: suspend CoroutineScope.() -> Unit): Job = launch(block = block)