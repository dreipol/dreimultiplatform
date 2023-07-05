package ch.dreipol.dreimultiplatform

expect class FileIdentifier

expect val FileIdentifier.fileName: String?
expect val FileIdentifier.filePath: String?

expect fun FileIdentifier.appendingPathComponent(component: String): FileIdentifier?
expect fun FileIdentifier.createDirectoriesIfNotExists()
expect fun FileIdentifier.exists(): Boolean
expect fun FileIdentifier.delete()
expect fun FileIdentifier.files(): List<FileIdentifier>

class FileError(message: String) : Exception(message)

expect object FileManager {
    fun stringFrom(file: FileIdentifier): String?
    fun byteArrayFrom(file: FileIdentifier): ByteArray?
    fun fileIdentifierFromPath(path: String): FileIdentifier?
}

