package azizi.ahmed.reader.packages.repository

import azizi.ahmed.reader.packages.data.DataOrException
import azizi.ahmed.reader.packages.data.Resource
import azizi.ahmed.reader.packages.model.MBook
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirestoreRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    private val booksCollection = firestore.collection("books")

    suspend fun getBooksForUser(userId: String): DataOrException<List<MBook>, Boolean, Exception> {
        val dataOrException = DataOrException<List<MBook>, Boolean, Exception>()

        try {
            dataOrException.loading = true
            dataOrException.data = booksCollection
                .whereEqualTo("user_id", userId)
                .get()
                .await()
                .documents
                .mapNotNull { documentSnapshot ->
                    documentSnapshot.toObject(MBook::class.java)?.copy(id = documentSnapshot.id)
                }
            dataOrException.loading = false
        } catch (firebaseFirestoreException: FirebaseFirestoreException) {
            dataOrException.loading = false
            dataOrException.e = firebaseFirestoreException
        }

        return dataOrException
    }

    suspend fun isBookSaved(userId: String, googleBookId: String): Boolean {
        return booksCollection
            .whereEqualTo("user_id", userId)
            .whereEqualTo("google_book_id", googleBookId)
            .limit(1)
            .get()
            .await()
            .documents
            .isNotEmpty()
    }

    suspend fun saveBook(book: MBook): Resource<String> {
        val userId = book.userId
        val googleBookId = book.googleBookId

        if (userId.isNullOrBlank() || googleBookId.isNullOrBlank()) {
            return Resource.Error("Missing user id or Google Books id.")
        }

        return try {
            if (isBookSaved(userId, googleBookId)) {
                return Resource.Success("Book already saved.")
            }

            val documentRef = booksCollection.add(book).await()
            documentRef.update("id", documentRef.id).await()
            Resource.Success(documentRef.id)
        } catch (exception: Exception) {
            Resource.Error(exception.message)
        }
    }

    suspend fun deleteSavedBook(userId: String, googleBookId: String): Resource<Boolean> {
        return try {
            val documents = booksCollection
                .whereEqualTo("user_id", userId)
                .whereEqualTo("google_book_id", googleBookId)
                .get()
                .await()
                .documents

            documents.forEach { documentSnapshot ->
                booksCollection.document(documentSnapshot.id).delete().await()
            }
            Resource.Success(true)
        } catch (exception: Exception) {
            Resource.Error(exception.message)
        }
    }

    suspend fun updateBook(bookId: String, updates: Map<String, Any?>): Resource<Boolean> {
        if (bookId.isBlank()) return Resource.Error("Missing book document id.")

        return try {
            booksCollection.document(bookId).update(updates).await()
            Resource.Success(true)
        } catch (exception: Exception) {
            Resource.Error(exception.message)
        }
    }

    suspend fun deleteBook(bookId: String): Resource<Boolean> {
        if (bookId.isBlank()) return Resource.Error("Missing book document id.")

        return try {
            booksCollection.document(bookId).delete().await()
            Resource.Success(true)
        } catch (exception: Exception) {
            Resource.Error(exception.message)
        }
    }
}
