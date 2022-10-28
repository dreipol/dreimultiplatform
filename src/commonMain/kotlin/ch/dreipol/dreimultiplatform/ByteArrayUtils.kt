package ch.dreipol.dreimultiplatform


fun ByteArray.hex(): String = joinToString(separator = " ") { it.hex() }