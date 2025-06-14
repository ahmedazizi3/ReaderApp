package azizi.ahmed.reader.packages.components.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReaderAppBar(
    modifier: Modifier = Modifier,
    title: String = "",
    showProfile: Boolean = true,
    navController: NavController,
    logout: () -> Unit = {},
    navigateToStatsScreen: () -> Unit = {}
) {
    TopAppBar(
        title = {
            // You can customize the title here
            Row(
                modifier = modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = modifier.width(140.dp), // Adjust width as needed
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    // Add profile icon or text here if needed
                    Icon(
                        imageVector  = Icons.Default.Person, // Replace with your profile icon painter
                        contentDescription = "Profile Icon",
                        modifier = Modifier
                            .size(35.dp)
                            .clickable {
                                navigateToStatsScreen()
                            }, // Add any modifiers you need
                        tint = Color(
                            0xff12cbdf
                        )

                    )

                    Text(
                        text = title,
                        color = Color(
                            0xff12cbdf
                        ), // Set the title color
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp
                    )
                }
                Icon(
                    modifier = modifier
                        .size(35.dp)
                        .clickable {
                            logout()
                        }, // Adjust size as needed
                    imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                    contentDescription = "Profile Icon",
                    tint = Color(
                        0xff12cbdf
                    ) // Set the icon color
                )
            }
        },
        actions = {
            // You can add actions like profile icon here
        },
        colors  = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White
        )
    )
}