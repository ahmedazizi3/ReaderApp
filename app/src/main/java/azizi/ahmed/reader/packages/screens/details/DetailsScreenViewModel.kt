package azizi.ahmed.reader.packages.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import azizi.ahmed.reader.packages.data.Resource
import azizi.ahmed.reader.packages.model.Item
import azizi.ahmed.reader.packages.model.MBook
import azizi.ahmed.reader.packages.repository.BooksRepository
import azizi.ahmed.reader.packages.repository.FirestoreRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailsScreenViewModel @Inject constructor(
    private val booksRepository: BooksRepository,
    private val firestoreRepository: FirestoreRepository,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {
    suspend fun getBookInfo(bookId: String): Resource<Item> {
        return booksRepository.getBookInfo(bookId)
    }

    suspend fun isBookSaved(googleBookId: String): Boolean {
        val userId = firebaseAuth.currentUser?.uid ?: return false
        return firestoreRepository.isBookSaved(userId, googleBookId)
    }

    fun saveBook(
        book: MBook,
        onSuccess: () -> Unit = {},
        onError: (String) -> Unit = {}
    ) {
        viewModelScope.launch {
            when (val result = firestoreRepository.saveBook(book)) {
                is Resource.Success -> onSuccess()
                is Resource.Error -> onError(result.message ?: "Could not save book.")
                is Resource.Loading -> Unit
            }
        }
    }

    fun deleteSavedBook(
        googleBookId: String,
        onSuccess: () -> Unit = {},
        onError: (String) -> Unit = {}
    ) {
        val userId = firebaseAuth.currentUser?.uid

        if (userId.isNullOrBlank()) {
            onError("No authenticated user.")
            return
        }

        viewModelScope.launch {
            when (val result = firestoreRepository.deleteSavedBook(userId, googleBookId)) {
                is Resource.Success -> onSuccess()
                is Resource.Error -> onError(result.message ?: "Could not remove book.")
                is Resource.Loading -> Unit
            }
        }
    }
}
