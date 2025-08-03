package com.example.tobedefined

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.tobedefined.common.modules.NavigationClasses
import com.example.tobedefined.ui.theme.ToBeDefinedTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.tobedefined.Login.LoginMVVM
import com.example.tobedefined.ProductsList.ProductsListVM
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MainActivity : ComponentActivity() {

    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        auth = Firebase.auth



        setContent {
            ToBeDefinedTheme {
                val navController = rememberNavController()
                var currentRoute by remember { mutableStateOf(navController.currentBackStackEntry?.destination?.route) }
                var productVM = viewModel<ProductsListVM>() // senao nao limpava pois estava a observar duas instancias do VM diferentes!!
                val loginMVVM: LoginMVVM = viewModel()
                val userState by loginMVVM.user.collectAsState()
                Scaffold(modifier = Modifier.fillMaxSize(),
                    topBar = {
                        if (currentRoute == NavigationClasses.NavigationRoutes.Login.nroute
                            || currentRoute == NavigationClasses.NavigationRoutes.Dashboard.nroute
                            || currentRoute == NavigationClasses.NavigationRoutes.CreateItem.nroute
                            || currentRoute == NavigationClasses.NavigationRoutes.EditItem.nroute
                        )
                            return@Scaffold
                        MyAppTopBar(
                            title = userState?.email.toString() ?: "",
                            navController = navController,
                            loginMVVM = loginMVVM // Pass the same ViewModel instance
                        )
                    },
                    floatingActionButton = {
                        Log.d("Route", navController.currentBackStackEntry?.destination?.route.toString())
                        Log.d("Route", NavigationClasses.NavigationRoutes.Login.nroute)
                        if (currentRoute == NavigationClasses.NavigationRoutes.Login.nroute
                            || currentRoute == NavigationClasses.NavigationRoutes.Dashboard.nroute
                            || currentRoute == NavigationClasses.NavigationRoutes.CreateItem.nroute
                            || currentRoute == NavigationClasses.NavigationRoutes.EditItem.nroute
                            )
                            return@Scaffold
                        FloatingButtonClean(productVM, navController)

                    }

                ) { innerPadding ->
                    Navigation(productVM, auth, loginMVVM, navController, updateCurrentRoute = {
                        currentRoute = it
                        Log.d("Route dd", currentRoute.toString())
                    })
                    //Log.d("Route", navController.currentBackStackEntry?.destination?.route.toString())
                }
            }
        }
    }
}
// TESTE!!!!!!!
@Composable
fun FloatingButtonClean(productVM: ProductsListVM = viewModel(), navHostController: NavHostController = rememberNavController(), modifier: Modifier = Modifier) {
    Column {
        Row {
            FloatingActionButton(
                onClick = {
                    Log.d("Route", "FloatingButtonClean")
                    productVM.clearList()
                },
                modifier = modifier,
                containerColor = Color.Black,
                contentColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Check",
                )
            }
            Spacer(modifier = Modifier.width(6.dp))
            FloatingActionButton(
                onClick = {
                    Log.d("Route", "FloatingButtonClean")
                    productVM.clearList()
                },
                modifier = modifier
            ) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = "Delete",
                )
            }

        }
        Spacer(modifier = Modifier.height(6.dp))

        FloatingActionButton(
            onClick = {
                Log.d("Route", "FloatingButtonClean")
                navHostController.navigate(NavigationClasses.NavigationRoutes.Dashboard.nroute)
            },
            modifier = modifier,
            containerColor = Color.Red,
            contentColor = Color.White
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Edit",
            )
        }



    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAppTopBar(
    title: String,
    navController: NavHostController, // To handle navigation on logout
    loginMVVM: LoginMVVM = viewModel() // Get the ViewModel instance
) {
    val userState by loginMVVM.user.collectAsState()
    val userEmail = userState?.email // Get the email from your UserUID/User object

    TopAppBar(
        title = {
            /*Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )*/
        },
        actions = {
            // Display email if available
            if (!userEmail.isNullOrBlank()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(end = 8.dp) // Add some padding
                ) {
                    Icon(
                        imageVector = Icons.Filled.AccountCircle,
                        contentDescription = "User Account",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = userEmail,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.End,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f, fill = false) // Allow shrinking but don't force expansion
                            .padding(end = 8.dp)
                    )
                }
            }
            // Logout Button
            IconButton(onClick = {
                loginMVVM.logout() // Call logout from ViewModel
                // Navigate to login screen and clear back stack
                navController.navigate(NavigationClasses.NavigationRoutes.Login.nroute) {
                    popUpTo(0) { // Clears the entire back stack
                        inclusive = true
                    }
                    launchSingleTop = true // Avoid multiple copies of Login screen
                }
            }) {
                Icon(
                    imageVector = Icons.Filled.ExitToApp,
                    contentDescription = "Logout"
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors( // Customize colors if needed
            containerColor = Color.Transparent,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    )
}
