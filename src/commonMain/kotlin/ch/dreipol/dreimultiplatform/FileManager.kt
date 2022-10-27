package ch.dreipol.dreimultiplatform

expect class FileIdentifier

expect fun FileIdentifier.appendingPathComponent(component: String): FileIdentifier?
expect val FileIdentifier.fileName: String?

expect object FileManager {
    fun stringFrom(file: FileIdentifier): String?
    fun byteArrayFrom(file: FileIdentifier): ByteArray?
}