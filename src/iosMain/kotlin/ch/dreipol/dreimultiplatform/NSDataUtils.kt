package ch.dreipol.dreimultiplatform

import kotlinx.cinterop.addressOf
import kotlinx.cinterop.convert
import kotlinx.cinterop.usePinned
import platform.Foundation.NSData
import platform.Foundation.create

fun ByteArray.toNSData() = this.usePinned {
    NSData.create(bytes = it.addressOf(0), length = this.size.convert())
}