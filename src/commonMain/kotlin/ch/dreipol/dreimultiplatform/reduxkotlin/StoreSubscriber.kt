package ch.dreipol.dreimultiplatform.reduxkotlin

import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import org.reduxkotlin.Store
import org.reduxkotlin.StoreSubscriber

fun <State : Any, T : Any?> Store<State>.subscribeChanges(selection: (State) -> T, onUpdate: (T) -> Unit): StoreSubscriber {
    val selector = select(selection)
    val onStateChanged = {
        selector.executeIfChanged { onUpdate(it) }
    }
    onStateChanged()
    return subscribe(onStateChanged)
}

fun <State : Any, T : Any?> Store<State>.flowOf(selection: (State) -> T): Flow<T> = callbackFlow {
    val cancelSubscription = subscribeChanges(selection = selection) { trySend(it) }
    awaitClose {
        cancelSubscription()
    }
}

private fun <State : Any, T : Any?> Store<State>.select(selection: (State) -> T) = object : Selector<T> {
    var valueHolder: ValueHolder<T> = ValueHolder.Nothing()
    override fun executeIfChanged(action: (T) -> Unit) {
        val newValue = selection(getState())
        if (valueHolder.isDifferentValue(newValue)) {
            action(newValue)
            valueHolder = ValueHolder.Value(newValue)
        }
    }
}

private sealed class ValueHolder<T : Any?> {
    class Nothing<T : Any?> : ValueHolder<T>()
    data class Value<T : Any?>(val value: T) : ValueHolder<T>()

    fun isDifferentValue(newValue: T): Boolean = when (this) {
        is Nothing -> true
        is Value -> value != newValue
    }
}

private interface Selector<T> {
    fun executeIfChanged(action: (T) -> Unit)
}