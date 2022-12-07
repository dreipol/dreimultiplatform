package ch.dreipol.dreimultiplatform.logging

import co.touchlab.kermit.Severity

interface ErrorLogging {
    fun logError( message: String, tag: String, throwable: Throwable?)
}