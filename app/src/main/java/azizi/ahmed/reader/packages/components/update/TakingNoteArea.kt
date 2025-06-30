package azizi.ahmed.reader.packages.components.update

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import azizi.ahmed.reader.R
import azizi.ahmed.reader.packages.model.MBook
import azizi.ahmed.reader.packages.screens.home.HomeScreenViewModel
import azizi.ahmed.reader.packages.utils.formateDate
import azizi.ahmed.reader.packages.utils.formateTime
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun TakingNoteArea(
    modifier: Modifier = Modifier,
    updateScreenViewModel: HomeScreenViewModel = hiltViewModel(),
    book: MBook = MBook(),
    bookItemId: String = "",
    navigateToHomeScreenWithRecomposition: () -> Unit = {},
) {
    val context = LocalContext.current
    val noteValue = remember {
        mutableStateOf("")
    }

    val isStartedReading = remember {
        mutableStateOf(false)
    }

    val isFinishedReading = remember {
        mutableStateOf(false)
    }


    val initialRating = remember(updateScreenViewModel.data.value) {
        updateScreenViewModel.data.value.data
            ?.firstOrNull {
                it.googleBookId == bookItemId
            }
            ?.rating?.toInt() ?: 0
    }

    val ratingValue = remember {
        mutableIntStateOf(initialRating)
    }

    val openDialog = remember {
        mutableStateOf(false)
    }


    SimpleForm(
        defaultValue = book.notes.toString().ifEmpty {
            "No note yet."
        },
    ) { note ->
        noteValue.value = note
    }

    Spacer(modifier = modifier.height(10.dp))

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextButton(
            onClick = {
                isStartedReading.value = true
            },
            modifier = modifier.weight(0.5f),
            colors = ButtonDefaults.textButtonColors(
                containerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent
            ),
            enabled = book.startedReading == null
        ) {
            if (book.startedReading == null) {
                if (!isStartedReading.value) {
                    Text(
                        text = "Start Reading",
                        color = Color(
                            0xff12cbdf
                        ),
                        fontSize = 20.sp
                    )
                } else {
                    Text(
                        text = "Started Reading",
                        color = Color(
                            0xff12cbdf
                        ),
                        fontSize = 20.sp
                    )
                }
            } else {
                Text(
                    text = "Started on: \n${formateDate(book.startedReading!!)}\nat: ${formateTime(book.startedReading!!)}",
                    color = Color(
                        0xff12cbdf
                    ),
                    fontSize = 20.sp
                )
            }
        }

        TextButton (
            onClick = {
                isFinishedReading.value = true
            },
            modifier = modifier.weight(0.5f),
            colors = ButtonDefaults.textButtonColors(
                containerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent
            ),
            enabled = book.finishedReading == null
        ) {
            if(book.finishedReading == null) {
                if(!isFinishedReading.value) {
                    Text(
                        text = "Mark As Read",
                        color = Color(
                            0xff12cbdf
                        ),
                        fontSize = 20.sp
                    )
                } else {
                    Text(
                        text = "Finished Reading",
                        color = Color(
                            0xff12cbdf
                        ),
                        fontSize = 20.sp
                    )
                }
            } else {
                Text(
                    text = "Finished on: \n${formateDate(book.finishedReading!!)}\nat: ${formateTime(book.finishedReading!!)}",
                    color = Color(
                        0xff12cbdf
                    ),
                    fontSize = 20.sp
                )
            }
        }
    }
    Spacer(modifier = modifier.height(6.dp))

    Text(
        text = "Rating",
        color = Color.DarkGray,
        fontSize = 25.sp
    )





    RatingBar(
        rating = initialRating
    ) { rating ->
        ratingValue.intValue = rating
    }


    Spacer(modifier = modifier.height(30.dp))

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        val changedNotes = book.notes != noteValue.value
        val changedRating = book.rating?.toInt() != ratingValue.intValue
        val isFinishedTimeStamp = if (isFinishedReading.value) Timestamp.now() else book.finishedReading
        val isStartedTimeStamp = if (isStartedReading.value) Timestamp.now() else book.startedReading

        val bookUpdate = changedNotes || changedRating || isStartedReading.value || isFinishedReading.value

        val bookToUpdate = hashMapOf(
            "finished_reading" to isFinishedTimeStamp,
            "notes" to noteValue.value,
            "rating" to ratingValue.intValue,
            "started_reading" to isStartedTimeStamp
        ) as Map<String, Any>
        IconButton(
            onClick = {},
            modifier = modifier.weight(0.5f),
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = Color.Transparent,
            )
        ) {
            Surface(
                modifier = modifier
                    .clip(
                        RoundedCornerShape(
                            topStartPercent = 50
                        )
                    )
                    .clickable {
                        if (bookUpdate) {
                            FirebaseFirestore
                                .getInstance()
                                .collection("books")
                                .document(book.id!!)
                                .update(bookToUpdate)
                                .addOnCompleteListener {
                                    Toast.makeText(context, "Book Updated", Toast.LENGTH_SHORT).show()

                                    navigateToHomeScreenWithRecomposition()
                                    Log.d("TAG", "TakingNoteArea: ")
                                }
                                .addOnFailureListener {
                                    Log.w("TAG", "Error updating the book: ")
                                }
                        }
                    },
                color = Color(0xff12cbdf)
            ) {
                Column(
                    modifier = modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Update",
                        color = Color.White,
                        fontSize = 20.sp
                    )
                }
            }
        }


        Spacer(modifier = modifier.width(40.dp))

        if (openDialog.value) {
            ShowingAlertDialog(
                title = "Delete Book",
                message = stringResource(id = R.string.sure) + "\n" + stringResource(id = R.string.action),
                openDialog = openDialog
            ) {
                FirebaseFirestore
                    .getInstance()
                    .collection("books")
                    .document(book.id!!)
                    .delete()
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            openDialog.value = false
                            Toast.makeText(context, "Book Deleted", Toast.LENGTH_SHORT).show()
                            navigateToHomeScreenWithRecomposition()
                        } else {
                            openDialog.value = false
                            Toast.makeText(context, "Error Deleting Book", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }

        IconButton(
            onClick = {},
            modifier = modifier.weight(0.5f),
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = Color.Transparent,
            )
        ) {
            Surface(
                modifier = modifier
                    .clip(
                        RoundedCornerShape(
                            topStartPercent = 50
                        )
                    )
                    .clickable {
                        openDialog.value = true
                    },
                color = Color(0xff12cbdf)
            ) {
                Column(
                    modifier = modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Delete",
                        color = Color.White,
                        fontSize = 20.sp
                    )
                }
            }
        }
    }
}