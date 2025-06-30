package azizi.ahmed.reader.packages.components.update


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import azizi.ahmed.reader.packages.data.DataOrException
import azizi.ahmed.reader.packages.model.MBook

@Composable
fun UpdateBookCard(
    modifier: Modifier = Modifier,
    bookInfo: DataOrException<List<MBook>, Boolean, Exception>,
    bookItemId: String
) {
    Column(
        modifier = modifier
            .width(350.dp)
            .background(Color.White)
    ) {
        Card(
            modifier = modifier
                .background(Color.White),
            shape = RoundedCornerShape(
                topStart = 80.dp,
                topEnd = 80.dp,
                bottomStart = 80.dp,
                bottomEnd = 80.dp
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            ),
            onClick = { /* Your action */ },
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            if(bookInfo.data != null) {
                CardListItem(
                    book = bookInfo.data!!.first { mBook ->
                        mBook.googleBookId == bookItemId
                    }
                )
            }
        }
    }
}




