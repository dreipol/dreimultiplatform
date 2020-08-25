package ch.dreipol.dreimultiplatform.reduxkotlin

import org.reduxkotlin.Selector
import org.reduxkotlin.SelectorSubscriberBuilder
import org.reduxkotlin.Store
import org.reduxkotlin.StoreSubscriber

/**
 * Helper function that creates a DSL for subscribing to changes in specific state fields and actions to take.
 * Inside the lambda there is access to the current state through the var `state`
 *
 * ex:
 *      val sel = selectorSubscriberFn {
 *          withSingleField({it.foo}, { actionWhenFooChanges() }
 *
 *          withAnyChange {
 *              //called whenever any change happens to state
 *              view.setMessage(state.barMsg) //state is current state
 *          }
 *      }
 */
fun <State : Any> selectorSubscriberFn(store: Store<State>, selectorSubscriberBuilderInit: SelectorSubscriberBuilder<State>.() -> Unit): StoreSubscriber {
    val subscriberBuilder: SelectorSubscriberBuilder<State> = SelectorSubscriberBuilder(store)
    subscriberBuilder.selectorSubscriberBuilderInit()
    return {
        subscriberBuilder.selectorList.forEach { entry ->
            @Suppress("UNCHECKED_CAST")
            (entry.key as Selector<State, *>).onChangeIn(store.getState()) { entry.value(store.getState()) }
        }
        subscriberBuilder.withAnyChangeFun?.invoke()
    }
}