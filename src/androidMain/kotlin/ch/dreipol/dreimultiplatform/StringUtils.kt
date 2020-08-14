package ch.dreipol.dreimultiplatform

actual fun formatString(string: String, vararg args: String): String {
    return String.format(string, *args)
}