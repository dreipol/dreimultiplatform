package ch.dreipol.dreimultiplatform.logging

import co.touchlab.kermit.LogWriter
import co.touchlab.kermit.Severity

class ErrorLogWriterDecorator(
    private val decorated: LogWriter,
    private val errorLogging: ErrorLogging,
    private val minSeverity: Severity = Severity.Error
) : LogWriter() {

    override fun log(severity: Severity, message: String, tag: String, throwable: Throwable?) {
        decorated.log(severity, message, tag, throwable)
        if (severity >= minSeverity) {
            errorLogging.logError(message, tag, throwable)
        }
    }
}