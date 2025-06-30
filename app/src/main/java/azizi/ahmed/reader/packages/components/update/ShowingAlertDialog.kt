package azizi.ahmed.reader.packages.components.update

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@Composable
fun ShowingAlertDialog(
    message: String = "",
    title: String = "",
    openDialog: MutableState<Boolean> = mutableStateOf(false),
    onConfirm: () -> Unit = {}
) {
    if (openDialog.value) {
        AlertDialog(
            containerColor = Color.White,
            textContentColor = Color(0xff12cbdf),
            titleContentColor = Color(0xff12cbdf),
            onDismissRequest = {
                openDialog.value = false
            },
            title = {
                Text(
                    text = title,
                    fontSize = 35.sp
                )
            },
            text = {
                Text(
                    text = message,
                    fontSize = 20.sp
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                        onConfirm()
                    }
                ) {
                    Text(
                        text = "Yes",
                        color = Color(0xff12cbdf),
                        fontSize = 20.sp
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                    }
                ) {
                    Text(
                        text = "No",
                        color = Color(0xff12cbdf),
                        fontSize = 20.sp
                    )
                }
            }
        )
    }
}