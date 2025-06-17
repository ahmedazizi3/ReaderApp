package azizi.ahmed.reader.packages.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
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
import com.google.firebase.auth.FirebaseAuth
import androidx.navigation.NavType // Import NavType
import androidx.navigation.navArgument // Import navArgument
import azizi.ahmed.reader.packages.screens.search.BookSearchViewModel

@Composable
fun ReaderNavigation() {
    val navController = rememberNavController()
    val startDestination = if (FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty()) {
        ReaderScreensHolder.LoginScreen.route
    } else {
        ReaderScreensHolder.HomeScreen.route
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
//        Define all the composable screens here

//        DetailsScreen is typically used to show details of a specific item
        // MODIFIED: Define the route to accept a bookId argument
        composable(
            route = "${ReaderScreensHolder.DetailsScreen.route}/{bookId}",
            arguments = listOf(navArgument("bookId") { type = NavType.StringType })
        ) { backStackEntry ->
            val bookId = backStackEntry.arguments?.getString("bookId")
            DetailsScreen(
                bookId = bookId
            )
        }

//        HomeScreen is the main screen of the app
        composable(ReaderScreensHolder.HomeScreen.route) {
            HomeScreen(
                logout = {
                    FirebaseAuth.getInstance().signOut().run {
                        navController.navigate(ReaderScreensHolder.LoginScreen.route) {
                            popUpTo(ReaderScreensHolder.HomeScreen.route) { inclusive = true }
                        }
                    }
                },
                navigateToStatsScreen = {
                    navController.navigate(ReaderScreensHolder.StatsScreen.route)
                },
                navigateToDetailsScreen = { bookId ->
                    navController.navigate("${ReaderScreensHolder.DetailsScreen.route}/$bookId")
                },
                navigateToSearchScreen = {
                    navController.navigate(ReaderScreensHolder.SearchScreen.route)
                }
            )
        }

//        LogInScreen is used for user authentication
        composable(ReaderScreensHolder.LoginScreen.route) {
            LogInScreen(
                navigateToSignUpScreen = {
                    navController.navigate(ReaderScreensHolder.SignUpScreen.route)
                },
                navigateToHomeScreen = {
                    navController.navigate(ReaderScreensHolder.HomeScreen.route)
                }
            )
        }

//        SearchScreen is used to search for items
        composable(ReaderScreensHolder.SearchScreen.route) {
            val searchViewModel = hiltViewModel<BookSearchViewModel>()
            SearchScreen(
                viewModel = searchViewModel,
                navigateToHomeScreen = {
                    navController.navigate(ReaderScreensHolder.HomeScreen.route) {
                        popUpTo(ReaderScreensHolder.SearchScreen.route) { inclusive = true }
                    }
                }
            )
        }

//        SignUpScreen is used for user registration
        composable(ReaderScreensHolder.SignUpScreen.route) {
            SignUpScreen(
                navigateToHomeScreen = {
                    navController.navigate(ReaderScreensHolder.HomeScreen.route)
                },
                navigateToLoginScreen = {
                    navController.navigate(ReaderScreensHolder.LoginScreen.route)
                }
            )
        }

//        StatsScreen is used to show statistics or analytics
        composable(ReaderScreensHolder.StatsScreen.route) {
            StatsScreen(
                navController = navController,
                navigateToHomeScreen = {
                    navController.navigate(ReaderScreensHolder.HomeScreen.route) {
                        popUpTo(ReaderScreensHolder.StatsScreen.route) { inclusive = true }
                    }
                }
            )
        }

//        UpdateScreen is used to update the app or user information
        composable(ReaderScreensHolder.UpdateScreen.route) {
            UpdateScreen()
        }
    }
}