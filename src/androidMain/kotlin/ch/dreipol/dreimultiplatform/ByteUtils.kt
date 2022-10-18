package ch.dreipol.dreimultiplatform

actual fun Byte.hex(): String = "%02x".format(this)