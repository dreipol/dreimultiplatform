package ch.dreipol.dreimultiplatform

import platform.Foundation.NSString
import platform.Foundation.stringWithFormat

actual fun formatAsHex(byte: Byte): String = NSString.stringWithFormat("%02x", byte)