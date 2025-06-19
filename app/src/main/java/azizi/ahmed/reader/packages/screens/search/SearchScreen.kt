package azizi.ahmed.reader.packages.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import azizi.ahmed.reader.packages.components.common.ReaderAppBar
import azizi.ahmed.reader.packages.components.search.SearchBookCard
import azizi.ahmed.reader.packages.components.search.InputField
import azizi.ahmed.reader.packages.model.Item

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: BookSearchViewModel = hiltViewModel(),
    navigateToDetailsScreen: (String) -> Unit = {},
    navigateToHomeScreen: () -> Unit = {}
) {
    val searchQueryState = remember {
        mutableStateOf("")
    }

    val keyboardController = LocalSoftwareKeyboardController.current

    val valid = remember(searchQueryState.value) {
        searchQueryState.value.trim().isNotEmpty()
    }

    val bookList = viewModel.listOfBooks



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
                    title = "Search Books...",
                    rowWidth = 250,
                    showProfile = false,
                    icon = Icons.Default.Home,
                    logout = navigateToHomeScreen
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
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top

                ) {

                    InputField(
                        valueState = searchQueryState,
                        labelId = "Enter a book name...",
                        enabled = true,
                        onAction = KeyboardActions {
                            if (!valid) return@KeyboardActions
                            viewModel.searchBooks(searchQueryState.value.trim())
                            searchQueryState.value = ""
                            keyboardController?.hide()
                        }
                    )
                    
                    Spacer(modifier = modifier.height(16.dp))

                    if (viewModel.isLoading) {
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
                        LazyColumn(
                            modifier = modifier
                                .fillMaxSize()
                                .padding(8.dp),
                            verticalArrangement = Arrangement.spacedBy(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            items(items = bookList) { book ->

                                SearchBookCard(
                                    modifier = modifier,
                                    book = book,
                                    navigateToDetailsScreen = {
                                        navigateToDetailsScreen(
                                            book.id
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