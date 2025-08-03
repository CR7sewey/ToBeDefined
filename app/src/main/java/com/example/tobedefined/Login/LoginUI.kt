package com.example.tobedefined.Login

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.tobedefined.R
import com.example.tobedefined.common.modules.NavigationClasses
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlin.text.isBlank

@Composable
fun LoginUI(navHostController: NavHostController, auth: FirebaseAuth, loginMVVM: LoginMVVM, modifier: Modifier = Modifier) {

    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    //val user = User(email.value, password.value)

    val context = LocalContext.current
    val scope = rememberCoroutineScope() // Get a coroutine scope


    val user = loginMVVM.user.collectAsState().value

    // Use LaunchedEffect to react to changes in userState for navigation
    LaunchedEffect(user) {
        if (user != null) { // If user is not null, login was successful
            Log.d("LoginUI", "User state changed, navigating to ProductsList.")
            navHostController.navigate(NavigationClasses.NavigationRoutes.ProductsList.nroute) {
                popUpTo(NavigationClasses.NavigationRoutes.Login.nroute) { inclusive = true }
            }
        }
    }

    // signIn function remains a suspend function
    /**
     *
     */
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = androidx.compose.ui.res.painterResource(id = R.drawable.images.toInt()),
            contentDescription = "Album Art",
            contentScale = androidx.compose.ui.layout.ContentScale.Crop,
            modifier = Modifier.size(240.dp)
        )
        Spacer(modifier = Modifier.height(56.dp))

        TextInput(
            value = email.value,
            onValueChange = {
                Log.d("A","A")
                email.value = it
            },
            label = "V"
        )
        Spacer(modifier = Modifier.height(6.dp))
        TextInput(
            value = password.value,
            onValueChange = {
                Log.d("A","A")
                password.value = it
            },
            label = "V"
        )
        Spacer(modifier = Modifier.height(6.dp))
        Button(
            onClick = {

                // It's good practice to ensure email and password are not blank
                if (email.value.isBlank() || password.value.isBlank()) {
                    Toast.makeText(
                        context,
                        "Email and password cannot be empty.",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@Button
                }

                //navHostController.navigate(NavigationClasses.NavigationRoutes.ProductsList.nroute)
                loginMVVM.login(email.value, password.value, context)

                if (loginMVVM.message.value.isNotBlank()) {
                    Toast.makeText(
                        context,
                        loginMVVM.message.value,
                        Toast.LENGTH_SHORT
                    ).show()
                }



                            }
        ) {
            Text(
                "Login"
            )
        }
    }

}

@Composable
fun TextInput(value: String = "", onValueChange: (String) -> Unit, label: String, modifier: Modifier = Modifier) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 96.dp, end = 96.dp)
            .focusRequester(focusRequester),
        value = value,
        onValueChange = { it -> onValueChange.invoke(it)
        },
        placeholder = { Text("Enter value") },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
                focusRequester.freeFocus()
                focusManager.clearFocus()
            }
        ),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = colorResource(id = R.color.purple_500),
            unfocusedIndicatorColor = colorResource(id = R.color.purple_500),
            focusedContainerColor = colorResource(id = R.color.white),
            unfocusedContainerColor = colorResource(id = R.color.white)
        ),
        shape = RoundedCornerShape(8.dp),
        singleLine = true,
        label = { Text(label) },
    )

}