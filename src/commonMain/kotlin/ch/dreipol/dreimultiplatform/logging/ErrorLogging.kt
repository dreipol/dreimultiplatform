package ch.dreipol.dreimultiplatform.logging

interface ErrorLogging {
    fun logError(
        message: String,
        tag: String,
        throwable: Throwable?,
    )
}