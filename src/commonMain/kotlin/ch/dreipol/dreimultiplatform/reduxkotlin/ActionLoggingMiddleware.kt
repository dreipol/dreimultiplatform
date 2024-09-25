package ch.dreipol.dreimultiplatform.reduxkotlin

import co.touchlab.kermit.Logger
import org.reduxkotlin.middleware

fun <State> actionLoggingMiddleware() = middleware<State> { _, next, action ->
    val actionLogName =
        when (action) {
            is Function<*> -> "Thunk Function"
            else -> action.toString()
        }
    Logger.d {
        "\n********************************************\n" +
            "******** DISPATCHED action: $actionLogName\n" +
            "********************************************"
    }
    next(action)
}