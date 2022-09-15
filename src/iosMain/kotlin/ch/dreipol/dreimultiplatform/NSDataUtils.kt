package ch.dreipol.dreimultiplatform

import kotlinx.cinterop.addressOf
import kotlinx.cinterop.allocArrayOf
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.usePinned
import platform.Foundation.NSData
import platform.Foundation.create
import platform.posix.memcpy

fun NSData.toByteArray(): ByteArray {
    return ByteArray(length.toInt()).apply {
        usePinned {
            memcpy(it.addressOf(0), bytes, length)
        }
    }
}

fun ByteArray.toNSData(): NSData = memScoped {
    return NSData.create(bytes = allocArrayOf(this@toNSData), length = size.toULong())
}

fun base64StringToByteArray(base64String: String): ByteArray {
    return NSData.create(base64Encoding = base64String)?.toByteArray() ?: throw IllegalArgumentException()
}
