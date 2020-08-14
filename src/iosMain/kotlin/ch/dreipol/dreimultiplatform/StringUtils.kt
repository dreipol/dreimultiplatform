package ch.dreipol.dreimultiplatform

import platform.Foundation.NSString
import platform.Foundation.stringWithFormat

actual fun formatString(string: String, vararg args: String): String {
    return NSString.stringWithFormat(string, *arrayOf(args as NSString))
}