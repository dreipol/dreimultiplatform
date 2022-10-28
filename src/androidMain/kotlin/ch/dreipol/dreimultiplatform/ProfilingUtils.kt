package ch.dreipol.dreimultiplatform

actual object Timing {
    actual fun nanoTime(): Long = System.nanoTime()
}