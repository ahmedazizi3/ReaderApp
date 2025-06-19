package azizi.ahmed.reader.packages.screens.details

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import azizi.ahmed.reader.packages.components.common.ReaderAppBar
import azizi.ahmed.reader.packages.data.Resource
import azizi.ahmed.reader.packages.model.Item
import azizi.ahmed.reader.packages.model.MBook
import coil.compose.AsyncImage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


@Composable
fun DetailsScreen(
    modifier: Modifier = Modifier,
    bookId: String,
    viewModel: DetailsScreenViewModel = hiltViewModel(),
    navigateToSearchScreen: () -> Unit = {}
) {

    fun parseHtml(htmlString: String?): String {
        return HtmlCompat.fromHtml(htmlString ?: "", HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
    }
    val bookInfo = produceState<Resource<Item>>(
        initialValue = Resource.Loading()
    ) {
        value = viewModel.getBookInfo(bookId)
    }.value

    val icon = remember { mutableStateOf(Icons.Default.FavoriteBorder) }
    val isSaved = remember { mutableStateOf(false) }
    val googleBookId = bookInfo.data?.id

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Scaffold(
            modifier = modifier.fillMaxSize(),
            topBar = {
                ReaderAppBar(
                    modifier = modifier,
                    title = "Book Details",
                    showProfile = false,
                    rowWidth = 245,
                    isDetailsScreen = true,
                    icon = icon.value,
                    save = {
                        isSaved.value = !isSaved.value
                        icon.value = if (isSaved.value) {
                            Icons.Default.Favorite
                        } else {
                            Icons.Default.FavoriteBorder
                        }
//                        Save the Book to the Firestore Database or Remove it from the Firestore Database
                        if(isSaved.value) {
//                            Save the Book to the Firestore Database
                            val book = MBook(
                                title = bookInfo.data?.volumeInfo?.title,
                                author = bookInfo.data?.volumeInfo?.authors?.joinToString(", "),
                                description = bookInfo.data?.volumeInfo?.description,
                                categories = bookInfo.data?.volumeInfo?.categories?.joinToString(", "),
                                publishedDate = bookInfo.data?.volumeInfo?.publishedDate,
                                pageCount = bookInfo.data?.volumeInfo?.pageCount.toString(),
                                notes = "",
                                rating = 0.0,
                                googleBookId = googleBookId,
                                userId = FirebaseAuth.getInstance().currentUser?.uid.toString(),
                                photoUrl = bookInfo.data?.volumeInfo?.imageLinks?.thumbnail?.replace("http://", "https://")
                            )
                            Log.d("PhotoURL Debug", "Saving photoUrl: ${bookInfo.data?.volumeInfo?.imageLinks?.thumbnail}")
                            saveToFirebase(book)
                        } else {
//                            Remove the Book from the Firestore Database

                        }
                    },
                    logout = navigateToSearchScreen
                )
            }
        ) {
            Surface(
                modifier = modifier
                    .fillMaxSize()
                    .padding(it)
                    .background(Color.White)
            ) {
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .background(Color.White),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    when (bookInfo) {
                        is Resource.Loading -> {
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
                        }

                        is Resource.Success -> {
                            val book = bookInfo.data?.volumeInfo

                            // Book Image
                            Surface(
                                modifier = modifier.size(150.dp),
                                shape = CircleShape,
                                color = Color.White,
                                shadowElevation = 10.dp,
                                tonalElevation = 10.dp
                            ) {
                                Log.d("ImageDebug", "Details Thumbnail: ${book?.imageLinks?.thumbnail?.replace("http://", "https://")}")

                                AsyncImage(
                                    model = book?.imageLinks?.thumbnail,
                                    contentDescription = "Book Image"
                                )
                            }

                            Spacer(modifier = modifier.height(10.dp))

                            // Title
                            Text(
                                text = book?.title ?: "No Title Available",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 16.dp)
                                    .align(Alignment.CenterHorizontally),
                                fontSize = 30.sp,
                                color = Color.Black,
                                fontWeight = FontWeight.ExtraBold,
                                textAlign = TextAlign.Center,
                                lineHeight = 35.sp
                            )



                            // Authors
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                        append("Authors: ")
                                    }
                                    append(book?.authors?.joinToString(", ") ?: "Unknown")
                                },
                                modifier = modifier.align(Alignment.Start),
                                fontSize = 20.sp,
                                color = Color.Black
                            )

                            // Page Count
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                        append("Page Count: ")
                                    }
                                    append(book?.pageCount?.toString() ?: "N/A")
                                },
                                modifier = modifier.align(Alignment.Start),
                                fontSize = 20.sp,
                                color = Color.Black
                            )

                            // Categories
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                        append("Categories: ")
                                    }
                                    append(book?.categories?.joinToString(", ") ?: "N/A")
                                },
                                modifier = modifier.align(Alignment.Start),
                                fontSize = 20.sp,
                                color = Color.Black,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )

                            // Published Date
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                        append("Published: ")
                                    }
                                    append(book?.publishedDate ?: "Unknown")
                                },
                                modifier = modifier.align(Alignment.Start),
                                fontSize = 20.sp,
                                color = Color.Black
                            )

                            Spacer(modifier = modifier.height(10.dp))

                            // Description
                            Surface(
                                modifier = modifier.weight(1f),
                                color = Color.White,
                                shadowElevation = 4.dp,
                                tonalElevation = 4.dp,
                                border = BorderStroke(
                                    width = 1.dp,
                                    color = Color.Black
                                )
                            ) {
                                LazyColumn(
                                    modifier = modifier
                                        .fillMaxSize()
                                        .padding(4.dp),
                                    verticalArrangement = Arrangement.Top,
                                    horizontalAlignment = Alignment.Start
                                ) {
                                    item {
                                        Text(
                                            text = parseHtml(book?.description),
                                            fontSize = 20.sp,
                                            color = Color.Black
                                        )
                                    }
                                }
                            }
                        }

                        is Resource.Error -> {
                            Text(
                                text = "Error loading book details.",
                                color = Color.Red,
                                fontSize = 18.sp,
                                modifier = modifier.align(Alignment.CenterHorizontally)
                            )
                        }
                    }
                }
            }
        }
    }
}




fun saveToFirebase(
    book: MBook
) {
    val db = FirebaseFirestore.getInstance()
    val dbCollection = db.collection("books")

    if (book.toString().isNotBlank()) {
        dbCollection.add(book).addOnSuccessListener { documentRef ->
            val docId = documentRef.id
            dbCollection.document(docId).update(
                hashMapOf("id" to docId) as Map<String, Any>
            ).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("SaveToFirebase", "Book saved successfully!")
                }
            }.addOnFailureListener {
                Log.d("SaveToFirebase", "Book saving failed!")
            }
        }
    }
}