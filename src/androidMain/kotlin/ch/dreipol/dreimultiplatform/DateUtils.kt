package ch.dreipol.dreimultiplatform

import java.text.DateFormatSymbols

actual fun getLocalizedMonthName(month: Int): String {
    return DateFormatSymbols().months[month - 1]
}

actual fun getLocalizedDayShort(day: Int): String {
    return DateFormatSymbols().shortWeekdays[isoWeekDayToJavaIndex(day)]
}

actual fun getLocalizedDay(day: Int): String {
    return DateFormatSymbols().weekdays[isoWeekDayToJavaIndex(day)]
}

private fun isoWeekDayToJavaIndex(isoWeekDay: Int): Int {
    if (isoWeekDay == 7) {
        return 1
    }
    return isoWeekDay + 1
}

