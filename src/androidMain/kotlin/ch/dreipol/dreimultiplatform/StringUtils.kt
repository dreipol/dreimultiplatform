package ch.dreipol.dreimultiplatform

actual fun formatString(string: String, args: List<String>): String {
    return String.format(string, *args.toTypedArray())
}