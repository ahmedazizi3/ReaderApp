package azizi.ahmed.reader.packages.repository

import azizi.ahmed.reader.packages.data.DataOrException
import azizi.ahmed.reader.packages.data.Resource
import azizi.ahmed.reader.packages.model.Item
import azizi.ahmed.reader.packages.network.BooksAPI
import javax.inject.Inject

class BooksRepository @Inject constructor(
    private val booksAPI: BooksAPI
) {

    suspend fun getBooks(searchQuery: String): Resource<List<Item>> {
        return try {
            Resource.Loading(data = true)
            val itemList = booksAPI.getAllBooks(searchQuery).items
            if (itemList.isNotEmpty()) Resource.Loading(data = false)
            Resource.Success(data = itemList)
        } catch (e: Exception) {
            Resource.Error(message = e.message.toString())
        }
    }


    suspend fun getBookInfo(bookId: String): Resource<Item> {
        val response = try {
            Resource.Loading(data = true)
            booksAPI.getBookInfo(bookId)

        } catch (e: Exception) {
            return Resource.Error(message = e.message.toString())
        }
        Resource.Loading(data = false)
        return Resource.Success(data = response)
    }
}