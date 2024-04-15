package ch.dreipol.dreimultiplatform

interface Localize {
    fun localize(string: String): String
    fun localize(string: String, vararg args: Any): String

}