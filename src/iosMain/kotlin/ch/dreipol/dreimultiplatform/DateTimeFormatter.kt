package ch.dreipol.dreimultiplatform

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toNSDateComponents
import platform.Foundation.NSCalendar
import platform.Foundation.NSDateFormatter

actual class DateTimeFormatter(private val formatter: NSDateFormatter) {
    actual companion object {
        actual fun ofPattern(pattern: String): DateTimeFormatter {
            val formatter = NSDateFormatter()
            formatter.dateFormat = pattern
            return DateTimeFormatter(formatter)
        }
    }

    actual fun format(date: LocalDate): String {
        val components = date.toNSDateComponents()
        val nsDate = NSCalendar.currentCalendar.dateFromComponents(components) ?: throw IllegalArgumentException()
        return formatter.stringFromDate(nsDate)
    }

    actual fun format(date: LocalDateTime): String {
        val components = date.toNSDateComponents()
        val nsDate = NSCalendar.currentCalendar.dateFromComponents(components) ?: throw IllegalArgumentException()
        return formatter.stringFromDate(nsDate)
    }
}