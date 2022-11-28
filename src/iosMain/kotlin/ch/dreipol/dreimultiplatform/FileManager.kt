package ch.dreipol.dreimultiplatform

import kotlinx.cinterop.CPointer
import kotlinx.cinterop.ObjCObjectVar
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value
import platform.Foundation.NSData
import platform.Foundation.NSError
import platform.Foundation.NSFileManager
import platform.Foundation.NSString
import platform.Foundation.NSURL
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.URLByAppendingPathComponent
import platform.Foundation.dataWithContentsOfURL
import platform.Foundation.lastPathComponent
import platform.Foundation.stringWithContentsOfURL

actual data class FileIdentifier(val url: NSURL)

actual fun FileIdentifier.appendingPathComponent(component: String): FileIdentifier? =
    url.URLByAppendingPathComponent(component)?.let { FileIdentifier(it) }

actual val FileIdentifier.fileName: String?
    get() = url.lastPathComponent


actual val FileIdentifier.filePath: String?
    get() = this.url.path

typealias ErrorPointer = CPointer<ObjCObjectVar<NSError?>>

private fun <R> fileManagerWithException(block: (fileManager: NSFileManager, errorPtr: ErrorPointer) -> R): R = memScoped {
    val errorPtr: ObjCObjectVar<NSError?> = alloc()
    val fileManager = NSFileManager.defaultManager
    val blockResult = block(fileManager, errorPtr.ptr)
    val error = errorPtr.value
    if (error != null) {
        throw FileError(error.localizedDescription)
    }
    blockResult
}

actual fun FileIdentifier.createDirectoriesIfNotExists() {
    fileManagerWithException { fileManager, errorPtr ->
        fileManager.createDirectoryAtURL(url, withIntermediateDirectories = true, attributes = null, error = errorPtr)
    }
}

actual fun FileIdentifier.exists(): Boolean {
    return filePath != null && NSFileManager.defaultManager.fileExistsAtPath(filePath!!)
}

actual fun FileIdentifier.delete() {
    fileManagerWithException { fileManager, errorPtr ->
        fileManager.removeItemAtURL(url, error = errorPtr)
    }
}

actual fun FileIdentifier.files(): List<FileIdentifier> = fileManagerWithException { fileManager, errorPtr ->
    fileManager.contentsOfDirectoryAtURL(
        url,
        error = errorPtr,
        includingPropertiesForKeys = null,
        options = 0
    )?.filterIsInstance<NSURL>() as List<NSURL>
}.map { FileIdentifier(it) }

actual object FileManager {
    actual fun stringFrom(file: FileIdentifier): String? =
        NSString.stringWithContentsOfURL(file.url, NSUTF8StringEncoding, null)

    actual fun byteArrayFrom(file: FileIdentifier): ByteArray? =
        NSData.dataWithContentsOfURL(file.url)?.toByteArray()

}