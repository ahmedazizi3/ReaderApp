package azizi.ahmed.reader.packages.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import azizi.ahmed.reader.packages.components.common.ReaderAppBar
import azizi.ahmed.reader.packages.components.login.FABContent

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    logout: () -> Unit = {},
    navigateToStatsScreen: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Scaffold(
            modifier = modifier
                .fillMaxSize()
                .background(
                    color = Color.White
                ),
            topBar = {
                ReaderAppBar(
                    modifier = modifier,
                    title = "Reader",
                    showProfile = true,
                    navController = navController, // Replace with actual NavController if needed
                    logout = logout, // Pass the logout function to the app bar
                    navigateToStatsScreen = navigateToStatsScreen // Pass the navigation function to the app bar
                )
            },
            floatingActionButton = {
                FABContent()
            }
        ) {
            Surface(
                modifier = modifier
                    .fillMaxSize()
                    .padding(it)
                    .background(
                        color = Color.White
                    )
            ) {
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .background(
                            color = Color.White
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top

                ) {
                    Text(
                        text = "Currently reading...",
                        modifier = modifier
                            .fillMaxWidth()
                            .align(Alignment.Start),
                        fontSize = 25.sp,
                        color = Color.Black
                    )
                }
            }
        }
    }
}