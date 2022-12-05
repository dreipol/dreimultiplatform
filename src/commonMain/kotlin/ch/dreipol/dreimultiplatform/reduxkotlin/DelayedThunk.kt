package ch.dreipol.dreimultiplatform.reduxkotlin

import ch.dreipol.dreimultiplatform.ioDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.reduxkotlin.Thunk
import kotlin.time.Duration

fun <State> delayed(duration: Duration, thunk: Thunk<State>): Thunk<State> = { dispatch, _, _ ->
     CoroutineScope(ioDispatcher).launch {
        delay(duration)
        dispatch(thunk)
    }
}

