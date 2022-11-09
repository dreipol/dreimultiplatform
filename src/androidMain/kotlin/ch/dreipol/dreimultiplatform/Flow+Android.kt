package ch.dreipol.dreimultiplatform

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector

actual class FlowRepresentation<T>(private val flow: Flow<T>) : Flow<T> {
    override suspend fun collect(collector: FlowCollector<T>) {
        flow.collect(collector)
    }
}

actual fun <T> Flow<T>.toRepresentation(): FlowRepresentation<T> = FlowRepresentation(this)