package ch.dreipol.dreimultiplatform

import android.content.Context

fun Context.getString(identifier: String): String {
    val resourceId = resources.getIdentifier(identifier, "string", packageName)
    return resources.getString(resourceId)
}

class Localizer(private val context: Context) : Localize {
    override fun localize(string: String): String {
        return context.getString(string)
    }
}