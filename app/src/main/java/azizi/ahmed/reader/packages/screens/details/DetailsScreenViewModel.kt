package azizi.ahmed.reader.packages.screens.details

import androidx.lifecycle.ViewModel
import azizi.ahmed.reader.packages.data.Resource
import azizi.ahmed.reader.packages.model.Item
import azizi.ahmed.reader.packages.repository.BooksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class DetailsScreenViewModel @Inject constructor(
    private val booksRepository: BooksRepository
) : ViewModel() {
    suspend fun getBookInfo(bookId: String): Resource<Item> {
        return booksRepository.getBookInfo(bookId)
    }


}