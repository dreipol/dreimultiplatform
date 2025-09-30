package ch.dreipol.dreimultiplatform.coroutines

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlin.time.Clock
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
fun countDownFlow(
    duration: Duration,
    period: Duration = 1.seconds,
) = flow {
    val delay = period.absoluteValue
    val start = Clock.System.now()

    while (true) {
        val elapsed = Clock.System.now() - start
        emit(duration - elapsed)
        delay(delay)
    }
}

@OptIn(ExperimentalTime::class)
fun countUpFlow(
    initial: Duration = 0.seconds,
    period: Duration = 1.seconds,
) = flow {
    val delay = period.absoluteValue
    val start = Clock.System.now()

    while (true) {
        val elapsed = Clock.System.now() - start
        emit(initial + elapsed)
        delay(delay)
    }
}