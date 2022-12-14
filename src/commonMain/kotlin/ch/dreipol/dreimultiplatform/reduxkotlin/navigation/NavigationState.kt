package ch.dreipol.dreimultiplatform.reduxkotlin.navigation

data class NavigationState(
    val screens: List<Screen>,
    @Deprecated("will be removed in future versions") val navigationDirection: NavigationDirection = NavigationDirection.POP,
    val sheet: Sheet? = null,
    val blockerScreen: Boolean = false,
) {
    val currentScreen = screens.last()
}

@Deprecated("will be removed in future versions")
enum class NavigationDirection {
    PUSH,
    POP
}

