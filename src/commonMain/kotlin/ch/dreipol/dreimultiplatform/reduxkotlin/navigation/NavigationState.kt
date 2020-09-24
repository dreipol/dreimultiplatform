package ch.dreipol.dreimultiplatform.reduxkotlin.navigation

data class NavigationState(val screens: List<Screen>, val navigationDirection: NavigationDirection) {
    val currentScreen = screens.last()
}

enum class NavigationDirection {
    PUSH,
    POP
}

