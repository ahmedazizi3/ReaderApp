package azizi.ahmed.reader.packages.screens.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LogInScreenViewModel: ViewModel() {
//    val loadingState = MutableStateFlow(LoadingState.IDLE)
    private val auth: FirebaseAuth = Firebase.auth


    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading
    private val _errorMessage = MutableLiveData<String?>(null)
    val errorMessage: LiveData<String?> = _errorMessage

    fun signInWithEmailAndPassword(
        email: String,
        password: String,
        navigateToHomeScreen: () -> Unit = {}
    )  = viewModelScope.launch {
        try {
            _loading.value = true
            _errorMessage.value = null
            auth.signInWithEmailAndPassword(email, password).await()
            navigateToHomeScreen()
        } catch (ex: Exception) {
            _errorMessage.value = ex.message ?: "Could not sign in. Please try again."
        } finally {
            _loading.value = false
        }
    }



}
