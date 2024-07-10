package ch.dreipol.dreimultiplatform

fun ByteArray.hex(): String = joinToString(separator = " ") { it.hex() }

object ByteArrayUtils {
    fun fromHex(string: String): ByteArray {
        val sanitized = string.filter { !it.isWhitespace() }
        if (sanitized.length % 2 != 0) {
            throw IllegalArgumentException("The input string should have an even number of characters")
        }

        return sanitized.chunked(2).map {
            val byteValue = it.toIntOrNull(radix = 16) ?: throw IllegalArgumentException("hex string contains illegal sequence ($it)")
            byteValue.toByte()
        }.toByteArray()
    }
}
