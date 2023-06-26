package ch.dreipol.dreimultiplatform.reduxkotlin.navigation

data class NavigationState(
    val screens: List<Screen>,
    val navigationDirection: NavigationDirection = NavigationDirection.POP,
    val sheet: Sheet? = null,
    val blockerScreen: Boolean = false,
) {
    val currentScreen = screens.last()
}
// used in Android for showing correct animation when
// navigating back from deep link jump
enum class NavigationDirection {
    PUSH,
    POP
}

