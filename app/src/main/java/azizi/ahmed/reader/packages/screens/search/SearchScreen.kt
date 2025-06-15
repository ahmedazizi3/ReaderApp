package azizi.ahmed.reader.packages.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import azizi.ahmed.reader.packages.components.common.ReaderAppBar
import azizi.ahmed.reader.packages.components.common.SearchBookCard
import azizi.ahmed.reader.packages.model.MBook

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    navigateToHomeScreen: () -> Unit = {}
) {
    val searchedBook = remember { 
        mutableStateOf("")  // State to hold the search query
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
                    title = "Search Books...",
                    rowWidth = 250,
                    showProfile = false,
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
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .background(
                            color = Color.White
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top

                ) {
                    OutlinedTextField(
                        singleLine = true,
                        keyboardActions = KeyboardActions.Default,
                        textStyle = TextStyle(
                            fontSize = 20.sp,
                        ),
                        value = searchedBook.value,
                        onValueChange = { 
                            searchedBook.value = it  // Update the search query
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        label = {
                            Text(
                                text = "Enter a book name...",
                                fontSize = 16.sp
                            )
                        },
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color(0xff12cbdf),
                            unfocusedIndicatorColor = Color.LightGray,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedLabelColor = Color(0xff12cbdf),
                            unfocusedLabelColor = Color.LightGray,
                            cursorColor = Color(0xff12cbdf),
                            focusedTextColor = Color(0xff12cbdf),
                            unfocusedTextColor = Color.LightGray,
                            focusedLeadingIconColor = Color(0xff12cbdf),
                            unfocusedLeadingIconColor = Color.LightGray
                        ),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search Icon"
                            )
                        }
                    )

                    Spacer(modifier = modifier.height(16.dp))

                    LazyColumn(
                        modifier = modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        verticalArrangement = Arrangement.spacedBy(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(
                            listOf(
                                MBook(
                                    title = "Book 1",
                                    author = "Author 1",
                                    publicationDate = "2020/01/01",
                                    coverImageUrl = "http://books.google.com/books/content?id=IDs63og2WpgC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api"
                                ),
                                MBook(
                                    title = "Book 2",
                                    author = "Author 2",
                                    publicationDate = "2021/01/01",
                                    coverImageUrl = "http://books.google.com/books/content?id=geSWl0y5OTAC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api"
                                ),
                                MBook(
                                    title = "Book 3",
                                    author = "Author 3",
                                    publicationDate = "2022/01/01",
                                    coverImageUrl = "http://books.google.com/books/content?id=c59gCUniP5gC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api"
                                ),
                                MBook(
                                    title = "Book 4",
                                    author = "Author 4",
                                    publicationDate = "2023/01/01",
                                    coverImageUrl = "http://books.google.com/books/content?id=-DMRqbn1RPIC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api"
                                )
                            )
                        ) { book ->
                            SearchBookCard(
                                modifier = modifier,
                                book = book,
                                // Assuming you have a list of books to display
                                // Replace with actual book data
                            )
                        }
                    }
                }
            }
        }
    }
}