package azizi.ahmed.reader.packages.components.login

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun FABContent(
    modifier: Modifier = Modifier,
    onTab: () -> Unit = { /* Default no-op */ } // Default no-op function for onTab
) {
    FloatingActionButton(
        onClick = { onTab() },
        shape = RoundedCornerShape(50.dp),
        containerColor = Color(
            0xff12cbdf // Example color, you can change it to your desired color
        )
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Add",
            modifier = Modifier
                .padding(16.dp),// Adjust padding as needed
            tint = Color.White // Set icon color to white
        )
    }
}