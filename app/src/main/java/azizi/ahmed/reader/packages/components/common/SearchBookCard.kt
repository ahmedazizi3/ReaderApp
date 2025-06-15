package azizi.ahmed.reader.packages.components.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
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
import azizi.ahmed.reader.packages.model.MBook
import coil.compose.AsyncImage

@Composable
fun SearchBookCard(
    modifier: Modifier = Modifier,
    book: MBook = MBook()
) {
    Column(
        modifier = modifier
            .height(140.dp)
            .width(350.dp)
            .background(Color.White)
    ) {
        Card(
            modifier = modifier
                .fillMaxSize()
                .background(Color.White)
                .clickable {  },
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
                    .fillMaxSize()
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
                    shadowElevation = 4.dp,
                    color = Color.LightGray
                ) {
                    AsyncImage(
                        model = book.coverImageUrl, // Replace with actual image URL
                        contentDescription = "Book Cover",
                        modifier = modifier
                            .fillMaxHeight()
                            .width(130.dp)
                            .background(Color.White)
                    )
                }
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .background(Color.White),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = book.title,
                        modifier = modifier.padding(8.dp),
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = book.author,
                        modifier = modifier.padding(start = 8.dp),
                        color = Color.Black,
                        fontSize = 16.sp
                    )
                    Text(
                        text = book.publicationDate,
                        modifier = modifier.padding(start = 8.dp),
                        color = Color.Black,
                        fontSize = 16.sp
                    )
                    Text(
                        text = "[Computers]",
                        modifier = modifier.padding(start = 8.dp),
                        color = Color.Black,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}