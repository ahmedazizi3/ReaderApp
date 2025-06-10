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

class SignUpScreenViewModel: ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth


    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    fun createUserWithEmailAndPassword(
        email: String,
        password: String,
        navigateToHomeScreen: () -> Unit = {}
    )  =  viewModelScope.launch {
        if (_loading.value == false) {
            _loading.value = true
            auth.createUserWithEmailAndPassword(
                email,
                password
            ).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = task.result?.user
                    val displayName = user?.email?.split('@')?.get(0)
                    val uid = user?.uid
                    createUser(uid, displayName)
                    navigateToHomeScreen()
                } else {
                    Log.d("FB", "createUserWithEmailAndPassword: ${task.exception?.message}")
                }
                _loading.value = false
            }
        }
    }

    private fun createUser(userID: String?, displayName: String?) {
        if (userID == null) {
            Log.e("FB", "UserID is null, cannot create Firestore user document.")
            return
        }
        val db = FirebaseFirestore.getInstance()
        val user = MUser(
            userID = userID,
            displayName = displayName ?: "Unknown",
            email = auth.currentUser?.email ?: "",
            imageUrl = "",
            isPremium = false,
            isDarkMode = false,
            isNotificationsEnabled = true
        ).toMap()

        db.collection("users").document(userID).set(user)
            .addOnSuccessListener {
                Log.d("FB", "User document created successfully in Firestore.")
            }
            .addOnFailureListener { e ->
                Log.e("FB", "Error creating user document in Firestore: ${e.message}")
            }
    }
}