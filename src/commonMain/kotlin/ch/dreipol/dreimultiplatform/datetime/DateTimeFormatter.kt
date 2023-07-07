package ch.dreipol.dreimultiplatform

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime

expect class DateTimeFormatter {
    companion object {
        fun ofPattern(pattern: String): DateTimeFormatter
    }

    fun format(date: LocalDate): String
    fun format(date: LocalDateTime): String
    fun parse(date: String): LocalDateTime
    fun parseDate(date: String): LocalDate
    fun parseTime(date: String): LocalTime
}