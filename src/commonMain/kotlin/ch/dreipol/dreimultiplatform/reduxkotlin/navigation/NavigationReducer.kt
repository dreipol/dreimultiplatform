package ch.dreipol.dreimultiplatform.reduxkotlin.navigation

fun navigateBack(state: NavigationState): NavigationState {
    if (state.sheet != null) {
        return state.copy(sheet = null)
    }
    val screens = state.screens.toMutableList()
    if (screens.size == 1) {
        return state
    }
    screens.removeAt(screens.lastIndex)
    return state.copy(screens = screens, navigationDirection = NavigationDirection.POP)
}