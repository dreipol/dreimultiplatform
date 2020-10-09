package ch.dreipol.dreimultiplatform

import kotlinx.cinterop.convert
import platform.Foundation.*

actual fun getLocalizedMonthName(month: Int): String {
    val calendar = NSCalendar.currentCalendar
    val dateComponents = calendar.components(NSCalendarUnitHour, fromDate = NSDate.date())
    dateComponents.setMonth(month.convert())
    val stringFormat = "MMMM"
    val format = NSDateFormatter.alloc() ?: return ""
    format.setDateFormat(stringFormat)
    val date = calendar.dateFromComponents(dateComponents) ?: return ""
    return format.stringFromDate(date)
}

actual fun getLocalizedDayShort(day: Int): String {
    val calendar = NSCalendar.currentCalendar
    val dateComponents = calendar.components(NSCalendarUnitHour, fromDate = NSDate.date())
    dateComponents.setDay(day.convert())
    val stringFormat = "DDD"
    val format: NSDateFormatter = NSDateFormatter.alloc() ?: return ""
    format.setDateFormat(stringFormat)
    val date = calendar.dateFromComponents(dateComponents) ?: return ""
    return format.stringFromDate(date)
}