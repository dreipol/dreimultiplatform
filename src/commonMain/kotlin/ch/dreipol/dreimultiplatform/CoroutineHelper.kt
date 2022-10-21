package ch.dreipol.dreimultiplatform

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

expect fun launchAndWait(block: suspend () -> Unit)

suspend fun <T> doAndAwait(`do`: suspend () -> Unit, await: suspend () -> T): T = coroutineScope {
    awaitAll<Either<Unit, T>>(
        async { Either.First(`do`()) },
        async { Either.Second(await()) }
    )[1].getSecond()
}