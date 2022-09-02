package ch.dreipol.dreimultiplatform

import java.io.File

actual typealias FileHandle = File

actual object FileManager {
    actual fun stringFrom(file: FileHandle): String? = file.readText()

    actual fun byteArrayFrom(file: FileHandle): ByteArray? = file.readBytes()
}