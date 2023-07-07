package ch.dreipol.dreimultiplatform.coroutines

import android.os.Looper
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

actual val uiDispatcher: CoroutineContext
    get() = Dispatchers.Main

actual val defaultDispatcher: CoroutineContext
    get() = Dispatchers.Default

actual val ioDispatcher: CoroutineContext
    get() = Dispatchers.IO

actual object ThreadUtils {
    actual val isOnMainThread: Boolean
        get() = Looper.myLooper() == Looper.getMainLooper()
}