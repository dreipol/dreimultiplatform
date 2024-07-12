package ch.dreipol.dreimultiplatform.reduxkotlin.navigation

import ch.dreipol.dreimultiplatform.reduxkotlin.subscribeChanges
import org.reduxkotlin.Store
import org.reduxkotlin.StoreSubscriber

interface Navigator<RootState : Any, NavigationState : AbstractNavigationState<*>> {
    val store: Store<RootState>
    fun updateNavigationState(navigationState: NavigationState)
    fun getNavigationState(): NavigationState
}

fun <
    RootState : Any,
    NavigationState : AbstractNavigationState<*>
    > Navigator<RootState, NavigationState>.subscribeNavigationState(): StoreSubscriber {
    return store.subscribeChanges({ getNavigationState() }) {
        updateNavigationState(getNavigationState())
    }
}