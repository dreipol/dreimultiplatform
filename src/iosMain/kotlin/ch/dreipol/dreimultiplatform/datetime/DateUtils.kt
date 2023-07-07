package ch.dreipol.dreimultiplatform.datetime

import kotlinx.cinterop.convert
import platform.Foundation.*

actual fun getLocalizedMonthName(month: Int): String {
    val calendar = NSCalendar.currentCalendar
    val dateComponents = calendar.components(NSCalendarUnitHour, fromDate = NSDate.date())
    dateComponents.setMonth(month.convert())
    val stringFormat = "MMMM"
    val format = NSDateFormatter()
    format.setDateFormat(stringFormat)
    val date = calendar.dateFromComponents(dateComponents) ?: return ""
    return format.stringFromDate(date)
}

actual fun getLocalizedDayShort(day: Int): String {
    val calendar = NSCalendar.autoupdatingCurrentCalendar
    return calendar.shortWeekdaySymbols[day] as? String ?: ""
}

actual fun getLocalizedDay(day: Int): String {
    val calendar = NSCalendar.autoupdatingCurrentCalendar
    return calendar.weekdaySymbols[day] as? String ?: ""
}