package ch.dreipol.dreimultiplatform

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

/**
 * This starts two suspend blocks at the same time and returns the result of the `await` one.
 * This can be used to wrap async functionality where the result is received through another channel, like a stream or a delegate method.
 */
suspend fun <T> doAndAwait(`do`: suspend () -> Unit, await: suspend () -> T): T = coroutineScope {
    awaitAll<Either<Unit, T>>(
        async { Either.First(`do`()) },
        async { Either.Second(await()) }
    ).last().second
}