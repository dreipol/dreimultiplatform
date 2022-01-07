package ch.dreipol.dreimultiplatform

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Runnable
import platform.UIKit.UIApplication
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_main_queue
import kotlin.coroutines.CoroutineContext

actual val uiDispatcher: CoroutineContext
    get() = IosMainDispatcher

actual val defaultDispatcher: CoroutineContext
    get() = IosMainDispatcher

/**
 * Needs to be implemented by the app delegate!
 */
interface ExceptionLogger {
    fun logException(message: String)
}

abstract class IosDispatcher : CoroutineDispatcher() {
    fun runSafely(block: Runnable) {
        try {
            block.run()
        } catch (t: Throwable) {
            t.cause?.let {
                val exceptionLogger = UIApplication.sharedApplication.delegate as? ExceptionLogger
                exceptionLogger?.logException(
                    "Uncaught exception (dispatched using ${this::class.qualifiedName}): " +
                        it.stackTraceToString()
                )
            }
            throw t
        }
    }
}

/**
 * iOS doesn't have a default UI thread dispatcher like [Dispatchers.Main], so we have to implement it ourself.
 */
private object IosMainDispatcher : IosDispatcher() {

    override fun dispatch(context: CoroutineContext, block: Runnable) {
        dispatch_async(dispatch_get_main_queue()) { runSafely(block) }
    }
}