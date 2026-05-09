package azizi.ahmed.reader.packages.screens.update

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import azizi.ahmed.reader.packages.components.common.ReaderAppBar
import azizi.ahmed.reader.packages.components.update.TakingNoteArea
import azizi.ahmed.reader.packages.components.update.UpdateBookCard
import azizi.ahmed.reader.packages.screens.home.HomeScreenViewModel

@Composable
fun UpdateScreen(
    modifier: Modifier = Modifier,
    bookItemId: String,
    updateScreenViewModel: HomeScreenViewModel = hiltViewModel(),
    navigateToHomeScreenWithRecomposition: () -> Unit = {},
    navigateBack: () -> Unit = {}
) {
    LaunchedEffect(bookItemId) {
        updateScreenViewModel.getAllBooksFromDatabase()
    }

    val bookInfo = updateScreenViewModel.data.value

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
                    title = "Update Book",
                    rowWidth = 210,
                    showProfile = false,
                    logout = {
                        navigateBack.invoke()
                    } // Pass the logout function to the app bar
                )
            }
        ) {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .background(color = Color.White)
                    .padding(it),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                if (bookInfo.loading == true) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = Color(
                                0xff12cbdf
                            )
                        )
                    }
                } else {
                    val selectedBook = bookInfo.data?.firstOrNull { mBook ->
                        mBook.googleBookId == bookItemId
                    }

                    Spacer(modifier = modifier.height(26.dp))
                    if (selectedBook != null) {
                        UpdateBookCard(
                            bookInfo = updateScreenViewModel.data.value,
                            bookItemId = bookItemId
                        )

                        Spacer(modifier = modifier.height(26.dp))

                        TakingNoteArea(
                            book = selectedBook,
                            bookItemId = bookItemId,
                            navigateToHomeScreenWithRecomposition = {
                                navigateToHomeScreenWithRecomposition.invoke()
                            }
                        )
                    } else {
                        Text(
                            text = "Book was not found in your reading list.",
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }
}
