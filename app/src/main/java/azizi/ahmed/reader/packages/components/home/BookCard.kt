package azizi.ahmed.reader.packages.components.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import azizi.ahmed.reader.packages.model.MBook
import coil.compose.AsyncImage

@Composable
fun BookCard(
    modifier: Modifier = Modifier,
    book: MBook = MBook(
        bookID = "",
        title = "",
        author = "",
        notes = "",
        description = "",
        coverImageUrl = "",
        genre = "",
        publicationDate = "",
        isFavorite = false,
        rating = 0.0,
        pageCount = 0
    ),
    onCardClick: (String) -> Unit = {}
) {

    val context = LocalContext.current

    val resources = context.resources

    val displayMetrics = resources.displayMetrics

    val screenWidth = displayMetrics.widthPixels / displayMetrics.density

    val spacing = 10.dp

    Column(
        modifier = modifier
            .height(260.dp)
            .width(200.dp)
    ) {
        Card(
            modifier = modifier
                .fillMaxSize()
                .clickable {
                    onCardClick.invoke(book.title)
                },
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Column(
                modifier = modifier.fillMaxWidth()
                    .width(
                        screenWidth.dp - (spacing * 2) // Adjust width based on screen size and spacing
                    ),
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                ) {
                    AsyncImage(
                        model = book.coverImageUrl, // Replace with book.coverImageUrl
                        contentDescription = book.title,
                        modifier = modifier
                            .height(150.dp)
                            .width(100.dp)
                    )

                    Spacer(modifier = modifier.width(45.dp))

                    Column(
                        modifier = modifier.padding(
                            start = 10.dp,
                            top = 10.dp
                        ),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.FavoriteBorder,
                            contentDescription = "Favorite Icon",
                            tint = Color.Black
                        )

                        BookRating(
                            modifier = modifier
                                .background(Color.White),
                            rating = 3.7
                        )
                    }
                }

                Text(
                    text = book.title,
                    modifier = modifier
                        .padding(
                        start = 10.dp,
                        top = 10.dp
                        )
                        .align(Alignment.Start),
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Text(
                    text = "by ${book.author}",
                    modifier = modifier
                        .padding(
                            start = 10.dp
                        )
                        .align(Alignment.Start),
                    color = Color.Black
                )


                Row {
                    Spacer(modifier = modifier.width(100.dp))
                    Row(
                        modifier = modifier,
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.Bottom,
                    ) {
                        Surface(
                            modifier = modifier
                                .clip(
                                    RoundedCornerShape(
                                        topStartPercent = 50
                                    )
                                )
                                .clickable {  },
                            color = Color(0xff12cbdf)
                        ) {
                            Column(
                                modifier = modifier
                                    .fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "Reading",
                                    color = Color.White,
                                    fontSize = 20.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}