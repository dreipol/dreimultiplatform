// Based on https://johnoreilly.dev/posts/kotlinmultiplatform-swift-combine_publisher-flow/
// See same link for implementation with Combine on iOS

package ch.dreipol.dreimultiplatform

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlin.coroutines.CoroutineContext

actual class FlowRepresentation<T>(private val flow: Flow<T>) {
    fun subscribe(
        onEach: (item: T) -> Unit, // The Swift 5.8 compiler cannot compile calls to subscribe if it's generic...
        onComplete: () -> Unit,
        onThrow: (error: Throwable) -> Unit,
        dispatcher: CoroutineContext
    ): Job {
        val job = Job()
        val scope = CoroutineScope(dispatcher + job)
        flow
            .cancellable()
            .onEach { onEach(it) }
            .catch { onThrow(it) }
            .onCompletion { onComplete() }
            // Do *not* use the resulting job. From documentation:
            // Note that the resulting value of launchIn is not used and the provided scope takes care of cancellation.
            .launchIn(scope)

        return job
    }

    fun subscribe(
        onEach: (item: T) -> Unit,
        onComplete: () -> Unit,
        onThrow: (error: Throwable) -> Unit
    ): Job = subscribe(onEach, onComplete, onThrow, ioDispatcher)

    // The Swift 5.8 compiler cannot compile calls to subscribe if it's generic...
    fun subscribe58(
        onEach: (item: Any?) -> Unit,
        onComplete: () -> Unit,
        onThrow: (error: Throwable) -> Unit
    ): Job = subscribe({ onEach(it) }, onComplete, onThrow, ioDispatcher)
}

actual fun <T> Flow<T>.toRepresentation(): FlowRepresentation<T> = FlowRepresentation(this.flowOn(uiDispatcher))