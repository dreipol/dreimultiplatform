package ch.dreipol.dreimultiplatform


fun ByteArray.toHex(): String = joinToString(separator = " ") { it.hex() }