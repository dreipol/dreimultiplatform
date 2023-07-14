package ch.dreipol.dreimultiplatform.coroutines

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

fun countdownFlow(duration: Duration, period: Duration = 1.seconds) = flow {
    var current = duration
    while (true) {
        emit(current)
        current -= period
        delay(period)
    }
}

fun countUpFlow(initial: Duration = 0.seconds, period: Duration = 1.seconds) = flow {
    var current = initial
    while (true) {
        emit(current)
        current += period
        delay(period)
    }
}