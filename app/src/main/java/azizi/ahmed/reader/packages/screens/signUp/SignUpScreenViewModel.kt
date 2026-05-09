package azizi.ahmed.reader.packages.screens.signUp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import azizi.ahmed.reader.packages.model.MUser
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class SignUpScreenViewModel: ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth


    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading
    private val _errorMessage = MutableLiveData<String?>(null)
    val errorMessage: LiveData<String?> = _errorMessage

    fun createUserWithEmailAndPassword(
        email: String,
        password: String,
        navigateToHomeScreen: () -> Unit = {}
    )  =  viewModelScope.launch {
        if (_loading.value == false) {
            _loading.value = true
            try {
                _errorMessage.value = null
                val result = auth.createUserWithEmailAndPassword(email, password).await()
                val user = result.user
                val userName = user?.email?.split('@')?.get(0)
                val uid = user?.uid
                createUser(uid, userName)
                navigateToHomeScreen()
            } catch (exception: Exception) {
                _errorMessage.value = exception.message ?: "Could not create account. Please try again."
            } finally {
                _loading.value = false
            }
        }
    }

    private suspend fun createUser(userID: String?, userName: String?) {
        if (userID == null) {
            Log.e("FB", "UserID is null, cannot create Firestore user document.")
            return
        }
        val db = FirebaseFirestore.getInstance()
        val user = MUser(
            userID = userID,
            userName = userName ?: "Unknown",
            firstName = userName ?: "Unknown",
            lastName = userName ?: "Unknown",
            email = auth.currentUser?.email ?: "",
            imageUrl = "",
            isPremium = false,
            isDarkMode = false,
            isNotificationsEnabled = true
        ).toMap()

        try {
            db.collection("users").document(userID).set(user).await()
        } catch (exception: Exception) {
            Log.e("FB", "Error creating user document in Firestore: ${exception.message}")
        }
    }
}
