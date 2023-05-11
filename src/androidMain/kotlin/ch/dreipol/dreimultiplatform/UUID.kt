package ch.dreipol.dreimultiplatform

actual object UUID {
    actual fun generateUUID(): String = java.util.UUID.randomUUID().toString()
}