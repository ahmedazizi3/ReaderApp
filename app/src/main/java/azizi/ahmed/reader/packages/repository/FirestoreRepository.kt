package azizi.ahmed.reader.packages.repository

import azizi.ahmed.reader.packages.data.DataOrException
import azizi.ahmed.reader.packages.model.MBook
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirestoreRepository @Inject constructor(
    private val queryBooks: Query
) {
    suspend fun getAllBooksFromDatabase(): DataOrException<List<MBook>, Boolean, Exception> {
        val dataOrException = DataOrException<List<MBook>, Boolean, Exception>()

        try {
            dataOrException.loading = true
            dataOrException.data = queryBooks.get().await().documents.map { documentSnapshot ->
                documentSnapshot.toObject(MBook::class.java)!!
            }
            if (!dataOrException.data.isNullOrEmpty()) dataOrException.loading = false
        } catch (firebaseFirestoreException: FirebaseFirestoreException) {
            dataOrException.e = firebaseFirestoreException
        }

        return dataOrException
    }
}