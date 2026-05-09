package azizi.ahmed.reader.packages.repository

import azizi.ahmed.reader.BuildConfig
import azizi.ahmed.reader.packages.data.Resource
import azizi.ahmed.reader.packages.model.Item
import azizi.ahmed.reader.packages.network.BooksAPI
import retrofit2.HttpException
import javax.inject.Inject

class BooksRepository @Inject constructor(
    private val booksAPI: BooksAPI
) {
    private val apiKey = BuildConfig.BOOKS_API_KEY.takeIf { it.isNotBlank() }

    suspend fun getBooks(searchQuery: String): Resource<List<Item>> {
        return try {
            Resource.Loading(data = true)
            val itemList = booksAPI.getAllBooks(searchQuery, apiKey).items
            if (itemList.isNotEmpty()) Resource.Loading(data = false)
            Resource.Success(data = itemList)
        } catch (e: HttpException) {
            Resource.Error(message = e.toUserMessage())
        } catch (e: Exception) {
            Resource.Error(message = e.message.toString())
        }
    }


    suspend fun getBookInfo(bookId: String): Resource<Item> {
        val response = try {
            Resource.Loading(data = true)
            booksAPI.getBookInfo(bookId, apiKey)

        } catch (e: HttpException) {
            return Resource.Error(message = e.toUserMessage())
        } catch (e: Exception) {
            return Resource.Error(message = e.message.toString())
        }
        Resource.Loading(data = false)
        return Resource.Success(data = response)
    }

    private fun HttpException.toUserMessage(): String {
        return when (code()) {
            429 -> "Google Books is rate-limiting requests. Add a Google Books API key or try again later."
            403 -> "Google Books rejected the request. Check that the API key is valid and the Books API is enabled."
            else -> message()
        }
    }
}
