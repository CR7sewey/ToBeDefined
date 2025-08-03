package com.example.tobedefined.Login

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tobedefined.common.data.User
import com.example.tobedefined.common.modules.NavigationClasses
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class LoginMVVM: ViewModel() {
    private val _user= MutableStateFlow<User?>(
        value = null
    )  // MutableLiveData<User>()
    val user: StateFlow<User?> = _user //LiveData

    private val _message = mutableStateOf("")
    val message: MutableState<String> = _message

    val firebaseAuth = FirebaseAuth.getInstance()
    val auth = firebaseAuth

    fun login(email: String, password: String, context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            //val context = LocalContext.current
            try {


                // Using withContext(Dispatchers.IO) if linkWithCredential involves network/disk I/O
                // Firebase SDKs often handle their own threading, but it's a good practice for clarity
                // and if you add more operations.
                /*val credential = EmailAuthProvider.getCredential(
                    email.trim(),
                    password.trim()
                ) // Use .value and trim
                val result = auth.currentUser!!.linkWithCredential(credential).result*/
                val result = withContext(Dispatchers.IO) {
                    auth.signInWithEmailAndPassword(email, password).await()
                }

                if (result.user != null) {
                    Log.d("Success", "signInWithEmail:success. User: ${result.user?.uid}")
                    // val userAuth = auth.currentUser // result.user is the same
                    _message.value = ""
                    _user.value = User(email, password)
                    Log.d("Success", "signInWithEmail:success. User: ${_user.value.toString()}")
                } else {
                    // This 'else' might not be hit if linkWithCredential throws an exception on failure
                    Log.w("Failure", "signInWithEmail: linkWithCredential result.user is null")
                    _message.value = "Authentication failed."
                }
            } catch (e: Exception) {
                // Catch specific Firebase exceptions if possible for better error handling
                Log.w("Failure", "signInWithEmail:failure", e)
                _message.value = "Authentication failed: ${e.message}"
                /*Toast.makeText(
                    context,
                    "Authentication failed: ${e.localizedMessage}",
                    Toast.LENGTH_LONG
                ).show()
*/
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            firebaseAuth.signOut()
            _user.value = null
            Log.d("LoginMVVM", "User logged out.")
        }
    }

}