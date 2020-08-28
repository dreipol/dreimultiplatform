package ch.dreipol.dreimultiplatform.reduxkotlin.navigation

import org.reduxkotlin.Store
import org.reduxkotlin.StoreSubscriber
import org.reduxkotlin.select

interface Navigator {
    var store: Store<Any>
    fun updateNavigationState(navigationState: NavigationState)
    fun getNavigationState(): NavigationState
}

fun Navigator.subscribeNavigationState() {
    val subscriber: StoreSubscriber = store.select({ getNavigationState() }) {
        updateNavigationState(getNavigationState())
    }
    store.subscribe(subscriber)
}