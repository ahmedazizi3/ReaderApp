package azizi.ahmed.reader.packages.navigation

sealed class ReaderScreensHolder(
    val route: String
) {
    data object LoginScreen: ReaderScreensHolder("loginScreen")
    data object SignUpScreen: ReaderScreensHolder("signUpScreen")
    data object HomeScreen: ReaderScreensHolder("homeScreen")
    data object DetailsScreen: ReaderScreensHolder("detailsScreen")
    data object SearchScreen: ReaderScreensHolder("searchScreen")
    data object StatsScreen: ReaderScreensHolder("statsScreen")
    data object UpdateScreen: ReaderScreensHolder("updateScreen")
}