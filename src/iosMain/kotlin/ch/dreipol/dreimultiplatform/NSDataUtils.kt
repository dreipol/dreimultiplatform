package ch.dreipol.dreimultiplatform

import kotlinx.cinterop.allocArrayOf
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.readBytes
import platform.Foundation.NSData
import platform.Foundation.create

@OptIn(kotlinx.cinterop.ExperimentalForeignApi::class)
fun NSData.toByteArray(): ByteArray {
    val len = this.length.toLong()

    if (len > Int.MAX_VALUE) {
        throw IllegalArgumentException("NSData is too large to fit in a ByteArray (max 2GB)")
    }

    if (len == 0L || this.bytes == null) {
        return ByteArray(0)
    }

    return this.bytes!!.readBytes(len.toInt())
}

@OptIn(kotlinx.cinterop.ExperimentalForeignApi::class)
fun ByteArray.toNSData(): NSData =
    memScoped {
        return NSData.create(bytes = allocArrayOf(this@toNSData), length = size.toULong())
    }

fun base64StringToByteArray(base64String: String): ByteArray {
    return NSData.create(base64Encoding = base64String)?.toByteArray() ?: throw IllegalArgumentException()
}