package ch.dreipol.dreimultiplatform.reduxkotlin

import ch.dreipol.dreimultiplatform.ThreadUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.reduxkotlin.Middleware
import org.reduxkotlin.middleware
import kotlin.coroutines.CoroutineContext

/*
 * Middleware that moves rest of the middleware/reducer chain to a coroutine using the given context.
 */
@Deprecated("Use MainThreadStore instead")
fun <State> coroutineMiddleware(context: CoroutineContext, checkMainThread: Boolean = false): Middleware<State> {
    val scope = CoroutineScope(context)
    return middleware { _, next, action ->
        if (checkMainThread && ThreadUtils.isOnMainThread) {
            next(action)
        } else {
            scope.launch {
                next(action)
            }
        }
    }
}