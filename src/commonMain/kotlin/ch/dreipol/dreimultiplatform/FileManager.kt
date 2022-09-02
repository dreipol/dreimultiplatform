package ch.dreipol.dreimultiplatform

expect class FileHandle

expect object FileManager {
    fun stringFrom(file: FileHandle): String?
    fun byteArrayFrom(file: FileHandle): ByteArray?
}