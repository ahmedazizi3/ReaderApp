package azizi.ahmed.reader.packages.screens.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import azizi.ahmed.reader.packages.components.common.ReaderAppBar
import azizi.ahmed.reader.packages.components.home.FABContent
import azizi.ahmed.reader.packages.components.home.BookCard
import azizi.ahmed.reader.packages.model.MBook
import com.google.firebase.auth.FirebaseAuth

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeScreenViewModel: HomeScreenViewModel = hiltViewModel(),
    logout: () -> Unit = {},
    navigateToStatsScreen: () -> Unit = {},
    navigateToSearchScreen: () -> Unit = {},
    navigateToUpdateScreen: () -> Unit = {}
) {

    var listOfBooks = emptyList<MBook>()
    val currentUser = FirebaseAuth.getInstance().currentUser

    if (!homeScreenViewModel.data.value.data.isNullOrEmpty()) {
        listOfBooks = homeScreenViewModel.data.value.data!!.toList().filter { mBook ->
            mBook.userId == currentUser?.uid.toString()
        }
        Log.d("Books", "HomeScreen: ${listOfBooks.toString()}")
    }


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
                    title = "Ahmed",
                    showProfile = true,
                    icon = Icons.AutoMirrored.Filled.ExitToApp,
                    logout = logout, // Pass the logout function to the app bar
                    navigateToStatsScreen = navigateToStatsScreen // Pass the navigation function to the app bar
                )
            },
            floatingActionButton = {
                FABContent(
                    onTab = navigateToSearchScreen
                )
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
                        )
                        .padding(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    Column(
                        modifier = modifier
                            .weight(0.5f)
                    ) {
                        Text(
                            text = "Currently reading...",
                            modifier = modifier
                                .fillMaxWidth()
                                .align(Alignment.Start)
                                .padding(bottom = 16.dp),
                            fontSize = 25.sp,
                            color = Color.Black
                        )

                        if (homeScreenViewModel.data.value.loading == true) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(0.5f),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(
                                    color = Color(
                                        0xff12cbdf
                                    )
                                )
                            }
                        } else {
                            LazyRow(
                                modifier = modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(20.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                items(
                                    items = listOfBooks
                                ) { book ->
                                    BookCard(
                                        modifier = modifier,
                                        book = book,
                                        navigateToUpdateScreen = {
                                            navigateToUpdateScreen.invoke()
                                        }
                                    )
                                }
                            }
                        }
                    }

                    Column(
                        modifier = modifier
                            .weight(0.5f)
                    ) {
                        Text(
                            text = "Reading List: ",
                            modifier = modifier
                                .fillMaxWidth()
                                .align(Alignment.Start)
                                .padding(
                                    top = 20.dp,
                                    bottom = 16.dp
                                ),
                            fontSize = 25.sp,
                            color = Color.Black
                        )

                        if (homeScreenViewModel.data.value.loading == true) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(0.5f),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(
                                    color = Color(
                                        0xff12cbdf
                                    )
                                )
                            }
                        } else {
                            LazyRow(
                                modifier = modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(20.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                items(
                                    items = listOfBooks
                                ) { book ->
                                    BookCard(
                                        modifier = modifier,
                                        book = book,
                                        navigateToUpdateScreen = {
                                            navigateToUpdateScreen.invoke()
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}