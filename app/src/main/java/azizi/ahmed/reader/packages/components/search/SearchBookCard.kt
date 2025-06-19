package azizi.ahmed.reader.packages.components.search

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import azizi.ahmed.reader.packages.model.Item
import coil.compose.AsyncImage

@Composable
fun SearchBookCard(
    modifier: Modifier = Modifier,
    book: Item,
    navigateToDetailsScreen: () -> Unit = {},
) {


    Column(
        modifier = modifier
            .width(350.dp)
            .background(Color.White)
    ) {
        Card(
            modifier = modifier
                .fillMaxSize()
                .background(Color.White)
                .clickable {
                    navigateToDetailsScreen()
                },
            shape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp,
                bottomStart = 16.dp,
                bottomEnd = 16.dp
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            )
        ) {
            // Content of the card goes here
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
                    .background(Color.White),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    modifier = modifier
                        .fillMaxHeight()
                        .width(100.dp),
                    shape = RoundedCornerShape(
                        topStart = 16.dp,
                        bottomStart = 16.dp,
                    ),
                    color = Color.LightGray
                ) {
                    Log.d("ImageDebug", "URL: ${book.volumeInfo.imageLinks.thumbnail}")
                    AsyncImage(
                        model = book.volumeInfo.imageLinks.thumbnail,
                                contentDescription = "Book Cover",
                        modifier = modifier
                            .fillMaxSize()
                            .background(Color.White)
                    )
                }
                Column(
                    modifier = modifier
                        .padding(8.dp)
                        .weight(1f),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = book.volumeInfo.title,
                        modifier = modifier.padding(8.dp),
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "By: ${book.volumeInfo.authors}",
                        modifier = modifier.padding(start = 8.dp),
                        color = Color.Black,
                        fontSize = 16.sp
                    )
                    Text(
                        text = "Date: ${book.volumeInfo.publishedDate}",
                        modifier = modifier.padding(start = 8.dp),
                        color = Color.Black,
                        fontSize = 16.sp
                    )
                    Text(
                        text = "Category: ${book.volumeInfo.categories}",
                        modifier = modifier.padding(start = 8.dp),
                        color = Color.Black,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}