package azizi.ahmed.reader.packages.screens.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LogInScreenViewModel: ViewModel() {
//    val loadingState = MutableStateFlow(LoadingState.IDLE)
    private val auth: FirebaseAuth = Firebase.auth


    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    fun signInWithEmailAndPassword(
        email: String,
        password: String,
        navigateToHomeScreen: () -> Unit = {}
    )  = viewModelScope.launch {
        try {
            auth.signInWithEmailAndPassword(
                email,
                password
            ) .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("FB", "signInWithEmailAndPassword: success")
                    navigateToHomeScreen()
                } else {
                    Log.d("FB", "signInWithEmailAndPassword: ${task.exception?.message}")
                }
            }

        } catch (ex: Exception) {
            Log.d("FB", "signInWithEmailAndPassword: ${ex.message}")
        }
    }



}