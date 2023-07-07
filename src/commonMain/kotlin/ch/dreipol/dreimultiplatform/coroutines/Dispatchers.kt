package ch.dreipol.dreimultiplatform.coroutines

import kotlin.coroutines.CoroutineContext

expect val defaultDispatcher: CoroutineContext

expect val uiDispatcher: CoroutineContext

expect val ioDispatcher: CoroutineContext

expect object ThreadUtils {
    val isOnMainThread: Boolean
}