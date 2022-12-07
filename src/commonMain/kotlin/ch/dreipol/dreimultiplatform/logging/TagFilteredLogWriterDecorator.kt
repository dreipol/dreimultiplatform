package ch.dreipol.dreimultiplatform.logging

import co.touchlab.kermit.LogWriter
import co.touchlab.kermit.Logger
import co.touchlab.kermit.Severity

class TagFilteredLogWriterDecorator private constructor(
    private val decorated: LogWriter,
    private val filters: Map<String, Severity>
) : LogWriter() {

    data class Builder(val decorated: LogWriter) {
        private val filters: MutableMap<String, Severity> = mutableMapOf()

        fun addFilter(logger: Logger, minSeverity: Severity) {
            filters[logger.tag] = minSeverity
        }

        fun build(): LogWriter {
            return TagFilteredLogWriterDecorator(decorated, filters)
        }
    }

    override fun log(severity: Severity, message: String, tag: String, throwable: Throwable?) {
        val severityFilter = filters[tag]
        if (severityFilter == null || severityFilter <= severity) {
            decorated.log(severity, message, tag, throwable)
        }
    }
}