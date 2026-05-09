package azizi.ahmed.reader.packages.screens.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import azizi.ahmed.reader.packages.data.DataOrException
import azizi.ahmed.reader.packages.data.Resource
import azizi.ahmed.reader.packages.model.MBook
import azizi.ahmed.reader.packages.repository.FirestoreRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val firestoreRepository: FirestoreRepository,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {
    val data: MutableState<DataOrException<List<MBook>, Boolean, Exception>> = mutableStateOf(
        DataOrException(
            listOf(),
            true,
            Exception("")
        )
    )

    init {
        getAllBooksFromDatabase()
    }

    fun getAllBooksFromDatabase() {
        val userId = firebaseAuth.currentUser?.uid

        if (userId.isNullOrBlank()) {
            data.value = DataOrException(
                data = emptyList(),
                loading = false,
                e = IllegalStateException("No authenticated user.")
            )
            return
        }

        viewModelScope.launch {
            data.value = data.value.copy(loading = true)
            data.value = firestoreRepository.getBooksForUser(userId)
        }
    }

    fun updateBook(
        bookId: String?,
        updates: Map<String, Any?>,
        onSuccess: () -> Unit = {},
        onError: (String) -> Unit = {}
    ) {
        if (bookId.isNullOrBlank()) {
            onError("Missing book document id.")
            return
        }

        viewModelScope.launch {
            when (val result = firestoreRepository.updateBook(bookId, updates)) {
                is Resource.Success -> {
                    getAllBooksFromDatabase()
                    onSuccess()
                }
                is Resource.Error -> onError(result.message ?: "Could not update book.")
                is Resource.Loading -> Unit
            }
        }
    }

    fun deleteBook(
        bookId: String?,
        onSuccess: () -> Unit = {},
        onError: (String) -> Unit = {}
    ) {
        if (bookId.isNullOrBlank()) {
            onError("Missing book document id.")
            return
        }

        viewModelScope.launch {
            when (val result = firestoreRepository.deleteBook(bookId)) {
                is Resource.Success -> {
                    getAllBooksFromDatabase()
                    onSuccess()
                }
                is Resource.Error -> onError(result.message ?: "Could not delete book.")
                is Resource.Loading -> Unit
            }
        }
    }
}
