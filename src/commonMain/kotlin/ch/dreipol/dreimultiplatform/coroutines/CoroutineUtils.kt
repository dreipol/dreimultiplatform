package ch.dreipol.dreimultiplatform.coroutines

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

/**
 * This starts two suspend blocks at the same time and returns the result of the `await` one.
 * This can be used to wrap async functionality where the result is received through another channel, like a stream or a delegate method.
 */
suspend fun <T> doAndAwait(`do`: suspend () -> Unit, await: suspend () -> T): T = coroutineScope {
    val context = async {
        await()
    }
    `do`()
    return@coroutineScope context.await()
}