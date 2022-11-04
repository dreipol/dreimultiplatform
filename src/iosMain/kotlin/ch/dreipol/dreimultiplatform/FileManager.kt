package ch.dreipol.dreimultiplatform

import platform.Foundation.*

actual data class FileIdentifier(val url: NSURL)

actual fun FileIdentifier.appendingPathComponent(component: String): FileIdentifier? =
    url.URLByAppendingPathComponent(component)?.let { FileIdentifier(it) }

actual val FileIdentifier.fileName: String?
    get() = url.lastPathComponent


actual val FileIdentifier.filePath: String?
    get() = this.url.path


actual object FileManager {
    actual fun stringFrom(file: FileIdentifier): String? =
        NSString.stringWithContentsOfURL(file.url, NSUTF8StringEncoding, null)

    actual fun byteArrayFrom(file: FileIdentifier): ByteArray? =
        NSData.dataWithContentsOfURL(file.url)?.toByteArray()

}