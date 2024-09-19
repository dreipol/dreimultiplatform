package ch.dreipol.dreimultiplatform

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.util.AtomicFile
import java.io.DataOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStreamWriter
import java.nio.file.Files


actual typealias FileIdentifier = File

actual val FileIdentifier.fileName: String?
    get() = this.name

actual val FileIdentifier.filePath: String?
    get() = this.path

actual fun FileIdentifier.appendingPathComponent(component: String): FileIdentifier? = File(this, component)

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

actual object FileManager {
    actual fun stringFrom(file: FileIdentifier): String? = file.readText()

    actual fun byteArrayFrom(file: FileIdentifier): ByteArray? = file.readBytes()

    actual fun fileIdentifierFromPath(path: String): FileIdentifier? = File(path)

    actual fun write(data: ByteArray, toFile: FileIdentifier, atomically: Boolean): Boolean =
        if (atomically) {
            AtomicFile(toFile).write(data)
        } else {
            runCatching {
                toFile.writeBytes(data)
            }.isSuccess
        }

    actual fun write(string: String, toFile: FileIdentifier, atomically: Boolean): Boolean =
        if (atomically) {
            AtomicFile(toFile).write(string)
        } else {
            runCatching {
                toFile.writeText(string)
            }.isSuccess
        }

    private fun AtomicFile.write(data: ByteArray): Boolean {
        var dataOutput: DataOutputStream? = null
        var outputStream: FileOutputStream? = null
        try {
            outputStream = startWrite()
            dataOutput = DataOutputStream(outputStream) // Wrapper stream
            dataOutput.write(data)
            finishWrite(outputStream) // Pass wrapper stream
            return true
        } catch (_: Throwable) {
            outputStream?.let { failWrite(it) }
            return false
        } finally {
            dataOutput?.close()
        }
    }

    private fun AtomicFile.write(string: String): Boolean {
        var dataOutput: OutputStreamWriter? = null
        var outputStream: FileOutputStream? = null
        try {
            outputStream = startWrite()
            dataOutput = OutputStreamWriter(outputStream) // Wrapper stream
            dataOutput.write(string)
            finishWrite(outputStream) // Pass wrapper stream
            return true
        } catch (_: Throwable) {
            outputStream?.let { failWrite(it) }
            return false
        } finally {
            dataOutput?.close()
        }
    }
}