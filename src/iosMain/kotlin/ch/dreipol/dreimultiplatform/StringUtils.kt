package ch.dreipol.dreimultiplatform

import kotlinx.cinterop.cstr
import platform.Foundation.NSString
import platform.Foundation.stringWithFormat

@OptIn(kotlinx.cinterop.ExperimentalForeignApi::class)
actual fun formatString(string: String, args: List<String>): String {
    if (args.size > 2) {
        throw IllegalArgumentException("StringUtils::formatString: Supports only two arguments at the moment")
    }
    return NSString.stringWithFormat(string, args.getOrElse(0) { "" }.cstr, args.getOrElse(1) { "" }.cstr)
}
