package azizi.ahmed.reader.packages.screens.update

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import azizi.ahmed.reader.packages.components.common.ReaderAppBar
import azizi.ahmed.reader.packages.data.DataOrException
import azizi.ahmed.reader.packages.model.MBook
import azizi.ahmed.reader.packages.screens.home.HomeScreenViewModel

@Composable
fun UpdateScreen(
    modifier: Modifier = Modifier,
    bookItemId: String,
    navController: NavHostController,
    updateScreenViewModel: HomeScreenViewModel = hiltViewModel(),
    navigateBack: () -> Unit = {}
) {

    val bookInfo = produceState<DataOrException<List<MBook>, Boolean, Exception>>(
        initialValue = DataOrException(
            loading = true,
            data = emptyList(),
            e = Exception("")
        )
    ) {
        value = updateScreenViewModel.data.value
    }.value

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Scaffold(
            modifier = modifier
                .fillMaxSize()
                .background(
                    color = Color.White
                ),
            topBar = {
                ReaderAppBar(
                    modifier = modifier,
                    title = "Update Book",
                    rowWidth = 210,
                    showProfile = false,
                    logout = {
                        navigateBack.invoke()
                    } // Pass the logout function to the app bar
                )
            }
        ) {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .background(color = Color.White)
                    .padding(it),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Log.d("UpdateScreen", "UpdateScreen: ${updateScreenViewModel.data.value.data.toString()}")
                if (bookInfo.loading == true) {
                    Log.d("UpdateScreen", "UpdateScreen: Loading...")
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = Color(
                                0xff12cbdf
                            )
                        )
                    }
                    bookInfo.loading = false
                } else {
                    Log.d("UpdateScreen", "UpdateScreen: ${bookInfo.data.toString()}")
                    Text(
                        text = updateScreenViewModel.data.value.data?.get(0)?.title.toString(),
                        color = Color.Black
                    )
                }
            }
        }
    }

}