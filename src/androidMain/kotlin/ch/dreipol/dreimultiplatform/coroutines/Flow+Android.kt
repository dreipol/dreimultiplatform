package ch.dreipol.dreimultiplatform.coroutines

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector

@Deprecated("Use SKIE instead")
actual class FlowRepresentation<T>(private val flow: Flow<T>) : Flow<T> {
    override suspend fun collect(collector: FlowCollector<T>) {
        flow.collect(collector)
    }
}

@Deprecated("Use SKIE instead")
actual fun <T> Flow<T>.toRepresentation(): FlowRepresentation<T> = FlowRepresentation(this)