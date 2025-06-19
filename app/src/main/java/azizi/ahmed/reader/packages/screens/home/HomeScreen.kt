package azizi.ahmed.reader.packages.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import azizi.ahmed.reader.packages.components.common.ReaderAppBar
import azizi.ahmed.reader.packages.components.home.FABContent
import azizi.ahmed.reader.packages.components.home.BookCard
import azizi.ahmed.reader.packages.model.MBook

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    logout: () -> Unit = {},
    navigateToStatsScreen: () -> Unit = {},
    navigateToDetailsScreen: (String) -> Unit = {},
    navigateToSearchScreen: () -> Unit = {}
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
                    Text(
                        text = "Currently reading...",
                        modifier = modifier
                            .fillMaxWidth()
                            .align(Alignment.Start)
                            .padding(bottom = 16.dp),
                        fontSize = 25.sp,
                        color = Color.Black
                    )

                    BookCard(
                        modifier = modifier
                            .align(Alignment.Start),
                        book = MBook(
                            title = "The Art of War",
                            author = "Ahmed Azizi",
                            photoUrl = "http://books.google.com/books/content?id=mP4ADQAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api"
                        )
                    )

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

                    LazyRow(
                        modifier = modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        items(
                            listOf(
                                MBook(
                                    title = "Book 1",
                                    author = "Author 1",
                                    photoUrl = "http://books.google.com/books/content?id=IDs63og2WpgC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api"
                                ),
                                MBook(
                                    title = "Book 2",
                                    author = "Author 2",
                                    photoUrl = "http://books.google.com/books/content?id=geSWl0y5OTAC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api"
                                ),
                                MBook(
                                    title = "Book 3",
                                    author = "Author 3",
                                    photoUrl = "http://books.google.com/books/content?id=c59gCUniP5gC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api"
                                ),
                                MBook(
                                    title = "Book 4",
                                    author = "Author 4",
                                    photoUrl = "http://books.google.com/books/content?id=-DMRqbn1RPIC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api"
                                )
                            )
                        ) { book ->
                            BookCard(
                                modifier = modifier,
                                book = book,
                                onCardClick = navigateToDetailsScreen
                            )
                        }
                    }
                }
            }
        }
    }
}