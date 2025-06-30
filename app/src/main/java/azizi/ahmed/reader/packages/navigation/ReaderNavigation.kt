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
import azizi.ahmed.reader.packages.screens.home.HomeScreenViewModel
import azizi.ahmed.reader.packages.screens.search.SearchScreenViewModel

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
        val detailsRoute = ReaderScreensHolder.DetailsScreen.route
        composable(
            route = "$detailsRoute/{bookId}",
            arguments = listOf(
                navArgument("bookId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            backStackEntry.arguments?.getString("bookId").let { bookId ->
                DetailsScreen(
                    bookId = bookId.toString(),
                    navigateToSearchOrHomeScreen = {
                        navController.popBackStack()
                    }
                )
            }
        }

//        HomeScreen is the main screen of the app

        composable(ReaderScreensHolder.HomeScreen.route) {
            val homeScreenViewModel = hiltViewModel<HomeScreenViewModel>()
            HomeScreen(
                homeScreenViewModel = homeScreenViewModel,
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
                navigateToUpdateScreen = {
                    navController.navigate(ReaderScreensHolder.UpdateScreen.route + "/$it")
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
            val searchViewModel = hiltViewModel<SearchScreenViewModel>()
            SearchScreen(
                searchScreenViewModel = searchViewModel,
                navigateToHomeScreen = {
                    navController.navigate(ReaderScreensHolder.HomeScreen.route) {
                        popUpTo(ReaderScreensHolder.SearchScreen.route) { inclusive = true }
                    }
                },
                navigateToDetailsScreen = { bookId ->
                    navController.navigate(ReaderScreensHolder.DetailsScreen.route + "/$bookId")
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
            val statsScreenViewModel = hiltViewModel<HomeScreenViewModel>()
            StatsScreen(
                statsScreenViewModel = statsScreenViewModel,
                navigateToHomeScreen = {
                    navController.navigate(ReaderScreensHolder.HomeScreen.route) {
                        popUpTo(ReaderScreensHolder.StatsScreen.route) { inclusive = true }
                    }
                }
            )
        }

//        UpdateScreen is used to update the app or user information
        val updateRoute = ReaderScreensHolder.UpdateScreen.route
        composable(
            route = "$updateRoute/{bookItemId}",
            arguments = listOf(
                navArgument("bookItemId") {
                    type = NavType.StringType
                }
            )
        ) { navBackStackEntry ->
            navBackStackEntry.arguments?.getString("bookItemId").let {
                UpdateScreen(
                    bookItemId = it.toString(),
                    navigateBack = {
                        navController.popBackStack()
                    },
                    navigateToHomeScreenWithRecomposition = {
                        navController.navigate(ReaderScreensHolder.HomeScreen.route)
                    }
                )
            }
        }
    }
}