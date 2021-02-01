package ch.dreipol.dreimultiplatform.reduxkotlin

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onSubscription
import org.reduxkotlin.Store
import org.reduxkotlin.StoreSubscription


class CancelSubscription(var subscription: StoreSubscription? = null)

operator fun CancelSubscription.invoke() {
    subscription?.invoke()
}

fun <State> Store<State>.asFlow(): Flow<State> {
    val flow = MutableStateFlow(state)
    val cancelSubscription = CancelSubscription()

    return flow.onSubscription {
        subscribe { flow.value = state }
            .also { cancelSubscription.subscription = it }
    }.onCompletion { cancelSubscription() }
}
