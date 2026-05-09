package azizi.ahmed.reader.packages.screens.home
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
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import azizi.ahmed.reader.packages.components.common.ReaderAppBar
import azizi.ahmed.reader.packages.components.home.BookCard
import azizi.ahmed.reader.packages.components.home.FABContent
import azizi.ahmed.reader.packages.model.MBook
import com.google.firebase.auth.FirebaseAuth

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeScreenViewModel: HomeScreenViewModel = hiltViewModel(),
    logout: () -> Unit = {},
    navigateToStatsScreen: () -> Unit = {},
    navigateToSearchScreen: () -> Unit = {},
    navigateToUpdateScreen: (String) -> Unit = {}
) {

    val currentUser = FirebaseAuth.getInstance().currentUser
    val currentUserName = currentUser?.email?.substringBefore('@') ?: "Reader"
    val listOfBooks = homeScreenViewModel.data.value.data.orEmpty()

    val addedBooks = listOfBooks.filter { mBook ->
        mBook.startedReading == null && mBook.finishedReading == null
    }

    val readingNow = listOfBooks.filter { mBook ->
        mBook.startedReading != null && mBook.finishedReading == null
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = Color.White
            ),
        topBar = {
            ReaderAppBar(
                modifier = modifier,
                title = currentUserName,
                showProfile = true,
                icon = Icons.AutoMirrored.Filled.ExitToApp,
                logout = logout,
                navigateToStatsScreen = navigateToStatsScreen
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
                    .padding(16.dp),
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
                            if (readingNow.isEmpty()) {
                                Box(
                                    modifier = modifier
                                        .fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "No books currently reading",
                                        fontSize = 28.sp,
                                        color = Color(0xff12cbdf)
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
                                        items = readingNow
                                    ) { book ->
                                        BookCard(
                                            modifier = modifier,
                                            book = book,
                                            navigateToUpdateScreen = {
                                                navigateToUpdateScreen.invoke(
                                                    book.googleBookId.toString()
                                                )
                                            }
                                        )
                                    }
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
                            if (addedBooks.isEmpty()) {
                                Box(
                                    modifier = modifier
                                        .fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "Your reading list is empty",
                                        fontSize = 28.sp,
                                        color = Color(0xff12cbdf)
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
                                        items = addedBooks
                                    ) { book ->
                                        BookCard(
                                            modifier = modifier,
                                            book = book,
                                            navigateToUpdateScreen = {
                                                navigateToUpdateScreen(
                                                    book.googleBookId.toString()
                                                )
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
