package ch.dreipol.dreimultiplatform

import kotlin.coroutines.CoroutineContext

expect val defaultDispatcher: CoroutineContext

expect val uiDispatcher: CoroutineContext

expect val ioDispatcher: CoroutineContext