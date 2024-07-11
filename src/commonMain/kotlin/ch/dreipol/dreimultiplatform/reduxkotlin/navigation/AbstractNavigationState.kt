package ch.dreipol.dreimultiplatform.reduxkotlin.navigation

abstract class AbstractNavigationState<Screen> {
    abstract val homeScreen: Screen
    abstract val otherScreens: List<Screen>
    val screens: List<Screen>
        get() = listOf(homeScreen) + otherScreens

    val currentScreen: Screen
        get() = screens.last()
}

// used in Android for showing correct animation when
// navigating back from deep link jump
enum class NavigationDirection {
    PUSH,
    POP
}

data class DirectionalNavigationState<Screen>(
    override val homeScreen: Screen,
    override val otherScreens: List<Screen> = emptyList(),
    val navigationDirection: NavigationDirection = NavigationDirection.POP,
) : AbstractNavigationState<Screen>()
