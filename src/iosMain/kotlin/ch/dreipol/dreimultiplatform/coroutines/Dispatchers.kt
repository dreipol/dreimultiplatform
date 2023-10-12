package ch.dreipol.dreimultiplatform.coroutines

import kotlinx.cinterop.convert
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Runnable
import platform.Foundation.NSThread
import platform.UIKit.UIApplication
import platform.darwin.*
import platform.posix.*
import kotlin.coroutines.CoroutineContext

actual val uiDispatcher: CoroutineContext
    get() = MainDispatcher

actual val defaultDispatcher: CoroutineContext
    get() = MainDispatcher

actual val ioDispatcher: CoroutineContext
    get() = QoSDispatcher(iOSDispatchQueue.QoSClass.USER_INITIATED)


object iOSDispatchQueue {
    enum class QoSClass(val value: UInt) {
        USER_INTERACTIVE(QOS_CLASS_USER_INTERACTIVE),
        USER_INITIATED(QOS_CLASS_USER_INITIATED),
        DEFAULT(QOS_CLASS_DEFAULT),
        UTILITY(QOS_CLASS_UTILITY),
        BACKGROUND(QOS_CLASS_BACKGROUND),
        UNSPECIFIED(QOS_CLASS_UNSPECIFIED)
    }

    @OptIn(kotlinx.cinterop.ExperimentalForeignApi::class)
    fun global(qos: QoSClass): dispatch_queue_global_t {
        return dispatch_get_global_queue(qos.value.convert(), 0.convert())
    }

    fun main(): dispatch_queue_main_t {
        return dispatch_get_main_queue()
    }
}


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
private object MainDispatcher : IosDispatcher() {

    override fun dispatch(context: CoroutineContext, block: Runnable) {
        dispatch_async(iOSDispatchQueue.main()) { runSafely(block) }
    }
}

private class QoSDispatcher(val qoSClass: iOSDispatchQueue.QoSClass) : IosDispatcher() {
    override fun dispatch(context: CoroutineContext, block: Runnable) {
        dispatch_async(iOSDispatchQueue.global(qoSClass), block::run)
    }
}

actual object ThreadUtils {
    actual val isOnMainThread: Boolean
        get() = NSThread.isMainThread
}
