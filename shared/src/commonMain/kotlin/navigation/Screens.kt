package navigation

sealed class Screen(val route: String) {
    data object Home : Screen(route = "home_screen")
    data object DetailsScreen: Screen(route = "details_screen")
}