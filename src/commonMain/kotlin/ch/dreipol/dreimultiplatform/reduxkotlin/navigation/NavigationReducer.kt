package ch.dreipol.dreimultiplatform.reduxkotlin.navigation

fun <Screen> navigateBack(state: DirectionalNavigationState<Screen>): DirectionalNavigationState<Screen> =
    state.copy(pushedScreens = state.pushedScreens.dropLast(1), navigationDirection = NavigationDirection.POP)
