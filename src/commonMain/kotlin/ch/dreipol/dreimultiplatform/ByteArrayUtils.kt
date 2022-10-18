package ch.dreipol.dreimultiplatform

expect fun formatAsHex(byte: Byte): String

fun ByteArray.toHex(): String = joinToString(separator = " ") { eachByte -> formatAsHex(eachByte) }