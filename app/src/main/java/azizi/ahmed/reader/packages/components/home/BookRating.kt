package azizi.ahmed.reader.packages.components.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun BookRating(
    modifier: Modifier = Modifier,
    rating: Double = 3.5,
) {
    Surface(
        modifier = modifier
            .height(70.dp)
            .background(Color.White)
            .padding(4.dp),
        shape = RoundedCornerShape(56.dp),
        shadowElevation = 6.dp
    ) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = modifier.background(Color.White),
                imageVector = Icons.Outlined.Star,
                contentDescription = "Book Rating",
                tint = Color.Black
            )
            Text(
                text = rating.toString(),
                color = Color.Black,
                modifier = Modifier.padding(top = 4.dp),
                fontWeight = FontWeight.Bold,
            )
        }
    }
}