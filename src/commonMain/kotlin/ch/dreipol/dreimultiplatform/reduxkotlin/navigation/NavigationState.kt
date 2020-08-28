package ch.dreipol.dreimultiplatform.reduxkotlin.navigation

data class NavigationState(val screens: List<Screen>, val navigationDirection: NavigationDirection)

enum class NavigationDirection {
    PUSH,
    POP
}

