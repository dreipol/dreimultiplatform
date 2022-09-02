package ch.dreipol.dreimultiplatform.reduxkotlin

import org.reduxkotlin.middleware

fun <State> convertThunkActionMiddleware() = middleware<State> { _, next, action ->
    val convertedAction = when (action) {
        is ThunkAction<*> -> action.thunk
        else -> action
    }
    next(convertedAction)
}