package ch.dreipol.dreimultiplatform

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toKotlinLocalDate
import kotlinx.datetime.toKotlinLocalDateTime
import kotlinx.datetime.toKotlinLocalTime

@RequiresApi(Build.VERSION_CODES.O)
actual class DateTimeFormatter(private val formatter: java.time.format.DateTimeFormatter) {
    actual companion object {
        actual fun ofPattern(pattern: String): DateTimeFormatter =
            DateTimeFormatter(java.time.format.DateTimeFormatter.ofPattern(pattern))
    }


    actual fun format(date: LocalDate): String =
        formatter.format(date.toJavaLocalDate())

    actual fun format(date: LocalDateTime): String =
        formatter.format(date.toJavaLocalDateTime())

    actual fun parse(date: String): LocalDateTime {
        return java.time.LocalDateTime.parse(date, formatter).toKotlinLocalDateTime()
    }

    actual fun parseDate(date: String): LocalDate {
        return java.time.LocalDate.parse(date, formatter).toKotlinLocalDate()
    }

    actual fun parseTime(date: String): LocalTime {
        return java.time.LocalTime.parse(date, formatter).toKotlinLocalTime()
    }

}