package azizi.ahmed.reader.packages.components.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun ReaderTextField(
    modifier: Modifier = Modifier,
    textFieldValue: MutableState<String>,
    readerLabel: String,
    isSingleLine: Boolean,
    keyboardOptions: KeyboardOptions,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        value = textFieldValue.value,
        onValueChange = {
            textFieldValue.value = it
        },
        label = {
            Text(
                text = readerLabel
            )
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedIndicatorColor = Color.LightGray,
            unfocusedIndicatorColor = Color.LightGray,
            focusedTextColor = Color.LightGray,
            cursorColor = Color.LightGray,
            unfocusedLabelColor = Color.LightGray,
            focusedLabelColor = Color.LightGray,
            unfocusedTextColor = Color.LightGray,

        ),
        singleLine = isSingleLine,
        keyboardOptions = keyboardOptions,
        visualTransformation = visualTransformation
    )
}