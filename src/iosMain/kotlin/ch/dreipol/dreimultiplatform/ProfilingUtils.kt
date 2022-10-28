package ch.dreipol.dreimultiplatform

import platform.CoreFoundation.CFAbsoluteTimeGetCurrent

actual object Timing {
    actual fun nanoTime(): Long = (CFAbsoluteTimeGetCurrent() * 1_000_000_000).toLong()
}
