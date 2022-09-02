package ch.dreipol.dreimultiplatform

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

actual val uiDispatcher: CoroutineContext
    get() = Dispatchers.Main

actual val defaultDispatcher: CoroutineContext
    get() = Dispatchers.Default

actual val ioDispatcher: CoroutineContext
    get() = Dispatchers.IO