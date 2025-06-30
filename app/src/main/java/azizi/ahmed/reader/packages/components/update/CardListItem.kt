package azizi.ahmed.reader.packages.components.update

import androidx.compose.foundation.background
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import azizi.ahmed.reader.packages.model.MBook
import coil.compose.AsyncImage

@Composable
fun CardListItem(
    modifier: Modifier = Modifier,
    book: MBook,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .background(Color.White)
            .padding(16.dp),
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
            AsyncImage(
                model = book.photoUrl.toString(),
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
                text = book.title.toString(),
                modifier = modifier.padding(8.dp),
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "By: ${book.author.toString()}",
                modifier = modifier.padding(start = 8.dp),
                color = Color.Black,
                fontSize = 16.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "Date: ${book.publishedDate.toString()}",
                modifier = modifier.padding(start = 8.dp),
                color = Color.Black,
                fontSize = 16.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}