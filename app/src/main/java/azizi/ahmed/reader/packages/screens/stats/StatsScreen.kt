package azizi.ahmed.reader.packages.screens.stats

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
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
import azizi.ahmed.reader.packages.components.common.ReaderAppBar
import azizi.ahmed.reader.packages.components.stats.BookRowStats
import azizi.ahmed.reader.packages.model.MBook
import azizi.ahmed.reader.packages.screens.home.HomeScreenViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun StatsScreen(
    modifier: Modifier = Modifier,
    statsScreenViewModel: HomeScreenViewModel = hiltViewModel(),
    navigateToHomeScreen: () -> Unit = {},
) {
    val currentUserName = FirebaseAuth.getInstance().currentUser?.email?.substringBefore('@') ?: "User"
    var books: List<MBook>
    val currentUser = FirebaseAuth.getInstance().currentUser


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
                    title = currentUserName,
                    showProfile = false,
                    rowWidth = 245,
                    icon = Icons.Default.Home,
                    logout = navigateToHomeScreen
                )
            }
        ) { it ->
            Surface(
                modifier = modifier
                    .fillMaxSize()
                    .padding(it)
                    .background(
                        color = Color.White
                    )
            ) {
                books = if (!statsScreenViewModel.data.value.data.isNullOrEmpty()) {
                    statsScreenViewModel.data.value.data!!.filter { mBook ->
                        (mBook.userId == currentUser?.uid)
                    }
                } else {
                    emptyList()
                }
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .background(
                            color = Color.White
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top

                ) {
                    Card(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        shape = CircleShape,
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 4.dp
                        ),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        )
                    ) {
                        val readBookList: List<MBook> = if (!statsScreenViewModel.data.value.data.isNullOrEmpty()) {
                            books.filter { mBook ->
                                (mBook.userId == currentUser?.uid) && (mBook.finishedReading != null)
                            }
                        } else {
                            emptyList()
                        }

                        val readingBooks = books.filter { mBook ->
                            (mBook.startedReading != null && mBook.finishedReading == null)
                        }

                        Column(
                            modifier = modifier
                                .padding(start = 25.dp, top = 4.dp, bottom = 4.dp),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = "Your Stats",
                                fontSize = 30.sp,
                                color = Color.Black
                            )
                            HorizontalDivider(
                               color = Color.LightGray
                            )
                            Text(
                                text = "You're reading: ${readingBooks.size} books",
                                color = Color.Black,
                                fontSize = 20.sp
                            )
                            HorizontalDivider(
                                color = Color.LightGray
                            )
                            Text(
                                text = "You've read: ${readBookList.size} books",
                                color = Color.Black,
                                fontSize = 20.sp
                            )
                        }
                    }
                    if (statsScreenViewModel.data.value.loading == true) {
                        LinearProgressIndicator()
                    } else {
                        Spacer(modifier = modifier.height(15.dp))
                        HorizontalDivider(
                            color = Color.LightGray
                        )

                        LazyColumn(
                            modifier = modifier
                                .fillMaxSize(),
                            contentPadding = PaddingValues(16.dp)
                        ) {
                            val readBooks: List<MBook> = if (!statsScreenViewModel.data.value.data.isNullOrEmpty()) {
                                statsScreenViewModel.data.value.data!!.filter {
                                    (it.userId == currentUser?.uid) && (it.finishedReading != null)
                                }
                            } else {
                                emptyList()
                            }

                            items(readBooks) { book ->
                                BookRowStats(book = book)
                                Spacer(modifier = modifier.height(10.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}