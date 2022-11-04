package ch.dreipol.dreimultiplatform

import java.io.File

actual typealias FileIdentifier = File

actual val FileIdentifier.filePath: String?
    get() = this.path

actual fun FileIdentifier.appendingPathComponent(component: String): FileIdentifier? =
    File(this, component)

actual object FileManager {
    actual fun stringFrom(file: FileIdentifier): String? = file.readText()

    actual fun byteArrayFrom(file: FileIdentifier): ByteArray? = file.readBytes()
}

actual val FileIdentifier.fileName: String?
    get() = this.name