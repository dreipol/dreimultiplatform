package ch.dreipol.dreimultiplatform.reduxkotlin.navigation

import ch.dreipol.dreimultiplatform.reduxkotlin.selectFixed
import org.reduxkotlin.Store
import org.reduxkotlin.StoreSubscriber

interface Navigator<RootState : Any, NavigationState : Any> {
    val store: Store<RootState>
    fun updateNavigationState(navigationState: NavigationState)
    fun getNavigationState(): NavigationState
}

fun <RootState : Any, NavigationState : Any> Navigator<RootState, NavigationState>.subscribeNavigationState(): StoreSubscriber {
    return store.selectFixed({ getNavigationState() }) {
        updateNavigationState(getNavigationState())
    }
}