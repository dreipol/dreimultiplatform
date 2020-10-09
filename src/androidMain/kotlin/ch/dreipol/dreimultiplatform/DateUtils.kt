package ch.dreipol.dreimultiplatform

import java.text.DateFormatSymbols

actual fun getLocalizedMonthName(month: Int): String {
    return DateFormatSymbols().months[month - 1]
}

actual fun getLocalizedDayShort(day: Int): String {
    return DateFormatSymbols().shortWeekdays[day - 1]
}