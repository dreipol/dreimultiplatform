package org.reduxkotlin


typealias Thunk<State> = (dispatch: Dispatcher, getState: GetState<State>, extraArg: Any?) -> Any
typealias ThunkMiddleware<State> = Middleware<State>

//fun <State> ThunkMiddleware<State>.withExtraArgument(arg: Any?) = createThunkMiddleware<State>(arg)

fun <State> createThunkMiddleware(extraArgument: Any? = null): ThunkMiddleware<State> =
    { store ->
        { next: Dispatcher ->
            { action: Any ->
                if (action is Function<*>) {
                    @Suppress("UNCHECKED_CAST")
                    val thunk = try {
                        (action as Thunk<*>)
                    } catch (e: ClassCastException) {
                        throw IllegalArgumentException("Dispatching functions must use type Thunk:", e)
                    }
                    thunk(store.dispatch, store.getState, extraArgument)
                } else {
                    next(action)
                }
            }
        }
    }