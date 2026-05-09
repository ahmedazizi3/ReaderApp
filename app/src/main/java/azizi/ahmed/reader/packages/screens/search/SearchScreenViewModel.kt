package azizi.ahmed.reader.packages.screens.search

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import azizi.ahmed.reader.packages.data.Resource
import azizi.ahmed.reader.packages.model.Item
import azizi.ahmed.reader.packages.repository.BooksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val booksRepository: BooksRepository
): ViewModel() {
    var listOfBooks: List<Item> by mutableStateOf(listOf())
    var isLoading: Boolean by mutableStateOf(false)
    var errorMessage: String? by mutableStateOf(null)


    fun searchBooks(query: String) {
        viewModelScope.launch {
            if (query.isEmpty()) return@launch
            isLoading = true
            try {
                errorMessage = null
                when (val response = booksRepository.getBooks(query)) {
                    is Resource.Success -> {
                        listOfBooks = response.data.orEmpty()
                        isLoading = false
                    }
                    is Resource.Error -> {
                        isLoading = false
                        listOfBooks = emptyList()
                        errorMessage = response.message ?: "Could not load books. Please try again."
                        Log.e("Network", "searchBooks: Failed getting books")
                    }
                    else -> {
                        isLoading = false
                    }
                }
            } catch (e: Exception) {
                isLoading = false
                errorMessage = e.message ?: "Could not load books. Please try again."
                Log.d("Network", "searchBooks: ${e.message.toString()}")
            }
        }
    }
}
