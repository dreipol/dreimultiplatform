package ch.dreipol.dreimultiplatform

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

expect class DateTimeFormatter {
    companion object {
        fun ofPattern(pattern: String): DateTimeFormatter
    }

    fun format(date: LocalDate): String
    fun format(date: LocalDateTime): String
}