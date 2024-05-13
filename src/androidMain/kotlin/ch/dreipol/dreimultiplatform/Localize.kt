package ch.dreipol.dreimultiplatform

import android.content.Context

fun Context.getString(identifier: String): String {
    val resourceId = stringId(identifier)
    return resources.getString(resourceId)
}

private fun Context.stringId(identifier: String): Int {
    return resources.getIdentifier(identifier, "string", packageName)
}

fun Context.getString(
    identifier: String,
    vararg args: Any,
): String {
    val stringId = stringId(identifier)
    return resources.getString(stringId, *args)
}

class Localizer(private val context: Context) : Localize {
    override fun localize(string: String): String = context.getString(string)

    override fun localize(
        string: String,
        vararg args: Any,
    ): String = context.getString(string, *args)
}