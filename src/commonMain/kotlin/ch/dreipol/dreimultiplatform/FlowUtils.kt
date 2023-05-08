package ch.dreipol.dreimultiplatform

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.flow
import kotlin.time.Duration
import kotlin.time.Duration.Companion.nanoseconds

fun <T> Sequence<T>.onFirst(action: (T) -> Unit): Sequence<T> = onEachIndexed { index, element ->
    if (index == 0) {
        action(element)
    }
}

fun <T> Flow<T>.throttle(duration: Duration): Flow<T> = throttleForce(duration) { false }

fun <T> Flow<T>.throttleForce(duration: Duration, force: ((T) -> Boolean)): Flow<T> = flow {
    var lastEvent: Long = 0
    this@throttleForce
        .cancellable()
        .collect {
            if ((Timing.nanoTime() - lastEvent).nanoseconds >= duration || force(it)) {
                lastEvent = Timing.nanoTime()
                emit(it)
            }
        }
}

expect class FlowRepresentation<T: Any>

expect fun <T: Any> Flow<T>.toRepresentation(): FlowRepresentation<T>
