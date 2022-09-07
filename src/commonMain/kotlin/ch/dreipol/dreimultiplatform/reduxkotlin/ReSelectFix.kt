package ch.dreipol.dreimultiplatform.reduxkotlin

import ch.dreipol.dreimultiplatform.freezeInstance
import org.reduxkotlin.Store
import org.reduxkotlin.StoreSubscriber
import org.reduxkotlin.selectors


fun <State : Any> Store<State>.selectFixed(selector: (State) -> Any, action: (Any) -> Unit): StoreSubscriber {
    selector.freezeInstance()
    action.freezeInstance()
    return this.selectors {
        select(selector, action)
    }.freezeInstance()
}