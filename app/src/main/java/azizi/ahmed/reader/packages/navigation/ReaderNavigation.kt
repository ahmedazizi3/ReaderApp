package azizi.ahmed.reader.packages.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import azizi.ahmed.reader.packages.screens.details.DetailsScreen
import azizi.ahmed.reader.packages.screens.home.HomeScreen
import azizi.ahmed.reader.packages.screens.login.LogInScreen
import azizi.ahmed.reader.packages.screens.search.SearchScreen
import azizi.ahmed.reader.packages.screens.signUp.SignUpScreen
import azizi.ahmed.reader.packages.screens.stats.StatsScreen
import azizi.ahmed.reader.packages.screens.update.UpdateScreen

@Composable
fun ReaderNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = ReaderScreensHolder.LoginScreen.route
    ) {
        composable(ReaderScreensHolder.DetailsScreen.route) {
            DetailsScreen()
        }
        composable(ReaderScreensHolder.HomeScreen.route) {
            HomeScreen()
        }
        composable(ReaderScreensHolder.LoginScreen.route) {
            LogInScreen {
                navController.navigate(ReaderScreensHolder.SignUpScreen.route)
            }
        }
        composable(ReaderScreensHolder.SearchScreen.route) {
            SearchScreen()
        }
        composable(ReaderScreensHolder.SignUpScreen.route) {
            SignUpScreen {
                navController.navigate(ReaderScreensHolder.LoginScreen.route)
            }
        }
        composable(ReaderScreensHolder.StatsScreen.route) {
            StatsScreen()
        }
        composable(ReaderScreensHolder.UpdateScreen.route) {
            UpdateScreen()
        }
    }
}