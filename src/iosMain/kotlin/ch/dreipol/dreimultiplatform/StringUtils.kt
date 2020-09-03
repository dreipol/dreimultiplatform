package ch.dreipol.dreimultiplatform

import kotlinx.cinterop.cstr
import platform.Foundation.NSString
import platform.Foundation.stringWithFormat

actual fun formatString(string: String, vararg args: String): String {
    return NSString.stringWithFormat(string, args.map { it.cstr })
}