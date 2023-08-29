package ch.dreipol.dreimultiplatform.datetime

import kotlin.time.Duration
import kotlin.time.Duration.Companion.nanoseconds
import kotlin.time.Duration.Companion.seconds

object DurationUtils {
    fun durationFromSeconds(seconds: Long): Duration = seconds.seconds
    fun durationFromNanoseconds(nanoseconds: Long): Duration = nanoseconds.nanoseconds
}