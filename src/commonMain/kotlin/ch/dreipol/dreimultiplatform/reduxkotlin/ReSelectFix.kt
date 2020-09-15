package ch.dreipol.dreimultiplatform.reduxkotlin

import org.reduxkotlin.Store
import org.reduxkotlin.StoreSubscriber
import org.reduxkotlin.selectors


fun <State : Any> Store<State>.selectFixed(selector: (State) -> Any, action: (Any) -> Unit): StoreSubscriber {
    return this.selectors {
        select(selector, action)
    }
}