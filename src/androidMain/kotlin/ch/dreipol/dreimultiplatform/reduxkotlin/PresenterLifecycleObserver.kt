package ch.dreipol.dreimultiplatform.reduxkotlin

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

/**
 * Lifecycle observer that dispatches attach/detach/clear actions for Presenter-Middleware
 */
class PresenterLifecycleObserver(val view: ViewWithProvider<*>) : LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onAttach() {
        rootDispatch(AttachView(view))
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onDetach() {
        rootDispatch(DetachView(view))
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onClear() {
        rootDispatch(ClearView(view))
    }

}