package ch.dreipol.dreimultiplatform

import android.os.Build
import androidx.annotation.RequiresApi
import java.io.File
import java.io.IOException
import java.nio.file.Files
import kotlin.streams.toList

actual typealias FileIdentifier = File

actual val FileIdentifier.fileName: String?
    get() = this.name

actual val FileIdentifier.filePath: String?
    get() = this.path

actual fun FileIdentifier.appendingPathComponent(component: String): FileIdentifier? =
    File(this, component)

@RequiresApi(Build.VERSION_CODES.O)
actual fun FileIdentifier.createDirectoriesIfNotExists() {
    val path = toPath()
    if (Files.notExists(path)) {
        Files.createDirectories(path)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
actual fun FileIdentifier.exists(): Boolean = Files.exists(toPath())

@RequiresApi(Build.VERSION_CODES.O)
actual fun FileIdentifier.delete() = Files.delete(toPath())

@RequiresApi(Build.VERSION_CODES.O)
actual fun FileIdentifier.files(): List<FileIdentifier> {
    if (!Files.isDirectory(toPath())) {
        throw IOException("The given FileIdentifier was not a directory.")
    }
    return Files.list(toPath()).map { it.toFile() }.toList()
}

actual fun String.toFileIdentifier(): FileIdentifier? =
    File(this)

actual object FileManager {
    actual fun stringFrom(file: FileIdentifier): String? = file.readText()

    actual fun byteArrayFrom(file: FileIdentifier): ByteArray? = file.readBytes()
}
