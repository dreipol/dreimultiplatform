package ch.dreipol.dreimultiplatform.reduxkotlin

import org.reduxkotlin.Store
import org.reduxkotlin.StoreSubscriber

fun <State : Any, T : Any> Store<State>.subscribeChanges(selection: (State) -> T, onUpdate: (T) -> Unit): StoreSubscriber {
    val selector = select(selection)
    val onStateChanged = {
        selector.getIfChanged()?.let { onUpdate(it) }
    }
    onStateChanged()
    return subscribe(onStateChanged)
}

private fun <State : Any, T : Any> Store<State>.select(selection: (State) -> T) = object : Selector<T> {
    var lastValue: T? = null
    override fun getIfChanged(): T? {
        val newValue = selection(getState())
        if (lastValue != newValue) {
            lastValue = newValue
            return newValue
        }
        return null
    }
}

private interface Selector<T> {
    fun getIfChanged(): T?
}