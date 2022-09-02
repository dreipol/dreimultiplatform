package ch.dreipol.dreimultiplatform

import platform.Foundation.*

actual data class FileHandle(val url: NSURL)

actual object FileManager {
    actual fun stringFrom(file: FileHandle): String? =
        NSString.stringWithContentsOfURL(file.url, NSUTF8StringEncoding, null)

    actual fun byteArrayFrom(file: FileHandle): ByteArray? =
        NSData.dataWithContentsOfURL(file.url)?.toByteArray()

}