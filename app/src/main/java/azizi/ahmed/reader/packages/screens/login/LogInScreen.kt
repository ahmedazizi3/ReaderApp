package azizi.ahmed.reader.packages.screens.login

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import azizi.ahmed.reader.packages.components.common.ReaderTextField

@Composable
fun LogInScreen(
    modifier: Modifier = Modifier,
    navigateToSignUpScreen: () -> Unit = {}
) {

    val email = rememberSaveable {
        mutableStateOf("")
    }
    val password = rememberSaveable {
        mutableStateOf("")
    }

    val visualTransformation = PasswordVisualTransformation()


    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = modifier
                .padding(top = 60.dp),
            text = "Reader",
            fontSize = 40.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.Red,
            fontStyle = FontStyle.Italic
        )

        Spacer(modifier = modifier.height(105.dp))

        ReaderTextField(
            textFieldValue = email,
            readerLabel = "Email",
            isSingleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            )
        )

        Spacer(modifier = modifier.height(15.dp))

        ReaderTextField(
            textFieldValue = password,
            readerLabel = "Password",
            isSingleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            visualTransformation = visualTransformation
        )

        Spacer(modifier = modifier.height(25.dp))

        Button(
            enabled = email.value.isNotEmpty() && password.value.isNotEmpty(),
            onClick = {
                Log.d("Form", "Login Screen, Email: $email, Password: $password")
            },
            colors = ButtonDefaults.buttonColors(
                containerColor =  Color.Red,
                contentColor = Color.White,
                disabledContainerColor = Color.LightGray,
                disabledContentColor = Color.White
            ),
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Login",
                fontSize = 25.sp
            )
        }

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "New to Reader?",
                color = Color.Black,
                fontSize = 20.sp
            )
            TextButton(
                onClick = {},
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.Blue
                ),
                contentPadding = PaddingValues()
            ) {
                Text(
                    text = "Sign Up",
                    modifier = modifier.clickable {
                        navigateToSignUpScreen()
                    },
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }

        }
    }


}