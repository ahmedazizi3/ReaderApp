package azizi.ahmed.reader.packages.components.update

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SimpleForm(
    defaultValue: String = "No note yet.",
    onSearch: (String) -> Unit = {},
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val noteValue = rememberSaveable { mutableStateOf(defaultValue) }
    val valid = remember(noteValue.value) {
        noteValue.value.trim().isNotEmpty()
    }

    // This Box allows us to detect clicks outside the TextField
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .pointerInput(Unit) {
                // When user taps anywhere, we clear focus
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent()
                        if (event.changes.any { it.pressed }) {
                            focusManager.clearFocus()
                        }
                    }
                }
            }
            .padding(16.dp)
    ) {
        // Detect when TextField loses focus
        var isFocused by remember { mutableStateOf(false) }

        LaunchedEffect(isFocused) {
            if (!isFocused && valid) {
                onSearch(noteValue.value.trim())
                keyboardController?.hide()
            }
        }

        OutlinedTextField(
            value = noteValue.value,
            onValueChange = { noteValue.value = it },
            label = { Text("Enter a note") },
            singleLine = false, // keep multi-line
            textStyle = TextStyle(fontSize = 20.sp),
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .onFocusChanged {
                    isFocused = it.isFocused
                },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Default // allow new line
            ),
            keyboardActions = KeyboardActions(), // no onDone needed
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color(0xff12cbdf),
                unfocusedIndicatorColor = Color.LightGray,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedLabelColor = Color(0xff12cbdf),
                unfocusedLabelColor = Color.LightGray,
                cursorColor = Color(0xff12cbdf),
                focusedTextColor = Color(0xff12cbdf),
                unfocusedTextColor = Color.LightGray,
                focusedLeadingIconColor = Color(0xff12cbdf),
                unfocusedLeadingIconColor = Color.LightGray,
                selectionColors = TextSelectionColors(
                    handleColor = Color(0xff12cbdf),
                    backgroundColor = Color.LightGray
                )
            )
        )
    }
}