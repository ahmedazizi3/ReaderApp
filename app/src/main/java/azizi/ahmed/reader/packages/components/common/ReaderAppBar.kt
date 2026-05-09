package azizi.ahmed.reader.packages.components.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReaderAppBar(
    modifier: Modifier = Modifier,
    title: String = "",
    showProfile: Boolean = true,
    rowWidth: Int = 160,
    isDetailsScreen: Boolean = false,
    icon: ImageVector = Icons.Default.Person,
    save: () -> Unit = {},
    logout: () -> Unit = {},
    navigateToStatsScreen: () -> Unit = {}
) {
    TopAppBar(
        modifier = modifier,
        navigationIcon = {
            if (showProfile) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Profile",
                    modifier = Modifier
                        .size(35.dp)
                        .clickable {
                            navigateToStatsScreen()
                        },
                    tint = Color(0xff12cbdf)
                )
            } else {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier
                        .size(35.dp)
                        .clickable {
                            logout()
                        },
                    tint = Color(0xff12cbdf)
                )
            }
        },
        title = {
            Text(
                text = title,
                color = Color(0xff12cbdf),
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp
            )
        },
        actions = {
            if (showProfile) {
                Icon(
                    modifier = Modifier
                        .size(35.dp)
                        .clickable {
                            logout()
                        },
                    imageVector = icon,
                    contentDescription = "Logout",
                    tint = Color(0xff12cbdf)
                )
            }

            if (isDetailsScreen) {
                Icon(
                    modifier = Modifier
                        .size(35.dp)
                        .clickable {
                            save()
                        },
                    imageVector = icon,
                    contentDescription = "Save book",
                    tint = Color(0xff12cbdf)
                )
            }
        },
        colors  = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White
        )
    )
}
