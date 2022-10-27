package ch.dreipol.dreimultiplatform

expect class FileHandle

expect fun FileHandle.appendingPathComponent(component: String): FileHandle?
expect val FileHandle.fileName: String?

expect object FileManager {
    fun stringFrom(file: FileHandle): String?
    fun byteArrayFrom(file: FileHandle): ByteArray?
}