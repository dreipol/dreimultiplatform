package ch.dreipol.dreimultiplatform.reduxkotlin.navigation

data class NavigationState(val screens: List<Screen>, val sheet: Sheet? = null) {
    val currentScreen = screens.last()
}

