package ch.dreipol.dreimultiplatform

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toJavaLocalDateTime

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

}