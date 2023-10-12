package ch.dreipol.dreimultiplatform.reduxkotlin

import ch.dreipol.dreimultiplatform.coroutines.ThreadUtils
import ch.dreipol.dreimultiplatform.coroutines.uiDispatcher
import kotlinx.coroutines.runBlocking
import org.reduxkotlin.GetState
import org.reduxkotlin.Store
import org.reduxkotlin.StoreEnhancer
import org.reduxkotlin.StoreSubscriber
import org.reduxkotlin.StoreSubscription
import org.reduxkotlin.TypedDispatcher
import org.reduxkotlin.TypedReducer
import org.reduxkotlin.TypedStore
import org.reduxkotlin.createTypedStore

public inline fun <State, reified Action: Any> createMainThreadStore(
    crossinline reducer: TypedReducer<State, Action>,
    preloadedState: State,
    noinline enhancer: StoreEnhancer<State>? = null
): TypedStore<State, Action> =
    MainThreadStore(createTypedStore(reducer, preloadedState, enhancer?.onMainThread()))

public class MainThreadStore<State, Action>(private val delegate: TypedStore<State, Action>) : TypedStore<State, Action> {
    override var dispatch: TypedDispatcher<Action> = { action ->
        runOnMainThread { delegate.dispatch(action) }
    }

    override val getState: GetState<State> = {
        runOnMainThread { delegate.getState() }
    }

    override val replaceReducer: (TypedReducer<State, Action>) -> Unit = { reducer ->
        runOnMainThread { delegate.replaceReducer(reducer) }
    }

    override val store: Store<State> = delegate.store

    override val subscribe: (StoreSubscriber) -> StoreSubscription = { subscriber ->
        val subscription = runOnMainThread { delegate.subscribe(subscriber) }
        val onMainThread = { runOnMainThread { subscription() } }
        onMainThread
    }

    private fun <T> runOnMainThread(block: () -> T): T =
        if (ThreadUtils.isOnMainThread) {
            block()
        } else {
            runBlocking(uiDispatcher) {
                block()
            }
        }
}

fun <State> StoreEnhancer<State>.onMainThread(): StoreEnhancer<State> = { storeCreator ->
    invoke { reducer, preloadedState, enhancer ->
        MainThreadStore(storeCreator(reducer, preloadedState, enhancer))
    }
}