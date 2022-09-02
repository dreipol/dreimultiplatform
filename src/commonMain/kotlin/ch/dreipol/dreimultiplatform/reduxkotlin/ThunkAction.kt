package ch.dreipol.dreimultiplatform.reduxkotlin

import org.reduxkotlin.Thunk

data class ThunkAction<State>(val thunk: Thunk<State>) : Action