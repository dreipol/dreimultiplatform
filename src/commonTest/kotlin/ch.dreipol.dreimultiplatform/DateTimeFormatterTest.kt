package ch.dreipol.dreimultiplatform

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime

class DateTimeFormatterTest {

    @Test
    fun testDateParsing() {
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        val date = "05.05.2023"
        val expected = LocalDate(2023,5,5)
        val result = formatter.parseDate(date)
        assertEquals(expected, result)
    }

    @Test
    fun testDateTimeParsing() {
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")
        val date = "05.05.2023 08:20:20"
        val expected = LocalDateTime(2023, 5, 5, 8, 20, 20)
        val result = formatter.parse(date)
        assertEquals(expected, result)
    }

    @Test
    fun testTimeParsing() {
        val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")
        val date = "08:20:20"
        val expected = LocalTime(8, 20, 20)
        val result = formatter.parseTime(date)
        assertEquals(expected, result)
    }

}