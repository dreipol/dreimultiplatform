package ch.dreipol.dreimultiplatform

import kotlin.math.floor
import kotlin.time.Duration
import kotlin.time.Duration.Companion.nanoseconds

expect object Timing {
    fun nanoTime(): Long
}

class StatisticsCollector() {
    data class Statistics(val average: Duration, val median: Duration, val percentile95: Duration)

    private val results = mutableListOf<Long>()

    suspend fun <R> measure(block: suspend () -> R): R {
        val start = Timing.nanoTime()
        val result = block()
        val end = Timing.nanoTime()

        results.add(end - start)

        return result
    }

    fun calculate(): Statistics {
        results.sort()

        return Statistics(
            average = (results.sum().toDouble() / results.size).nanoseconds,
            median = results[results.size / 2].nanoseconds,
            percentile95 = results[floor(results.size * 0.95).toInt()].nanoseconds
        )
    }
}