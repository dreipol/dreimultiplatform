package ch.dreipol.dreimultiplatform.reduxkotlin.navigation

import ch.dreipol.dreimultiplatform.reduxkotlin.selectFixed
import org.reduxkotlin.Store
import org.reduxkotlin.StoreSubscriber
import org.reduxkotlin.selectors

interface Navigator<RootState : Any> {
    val store: Store<RootState>
    fun updateNavigationState(navigationState: NavigationState)
    fun getNavigationState(): NavigationState
}

fun Navigator<*>.subscribeNavigationState() {
    store.selectFixed({ getNavigationState() }) {
        updateNavigationState(getNavigationState())
    }
}