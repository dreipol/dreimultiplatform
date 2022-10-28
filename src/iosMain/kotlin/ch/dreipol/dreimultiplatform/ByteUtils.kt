package ch.dreipol.dreimultiplatform

import platform.Foundation.NSString
import platform.Foundation.stringWithFormat

actual fun Byte.hex(): String = NSString.stringWithFormat("%02x", this)