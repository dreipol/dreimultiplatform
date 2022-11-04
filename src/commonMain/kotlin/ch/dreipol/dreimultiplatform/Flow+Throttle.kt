package ch.dreipol.dreimultiplatform

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.flow
import kotlin.time.Duration
import kotlin.time.Duration.Companion.nanoseconds

fun <T> Flow<T>.throttle(duration: Duration): Flow<T> = flow {
    var lastEvent: Long = 0

    this@throttle
        .cancellable()
        .collect {
            if ((Timing.nanoTime() - lastEvent).nanoseconds >= duration) {
                lastEvent = Timing.nanoTime()
                emit(it)
            }
    }
}