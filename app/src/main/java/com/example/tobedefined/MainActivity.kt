package com.example.tobedefined

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.tobedefined.common.modules.NavigationClasses
import com.example.tobedefined.ui.theme.ToBeDefinedTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tobedefined.ProductsList.ProductsListVM

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ToBeDefinedTheme {
                val navController = rememberNavController()
                var currentRoute by remember { mutableStateOf(navController.currentBackStackEntry?.destination?.route) }
                var productVM = viewModel<ProductsListVM>() // senao nao limpava pois estava a observar duas instancias do VM diferentes!!

                Scaffold(modifier = Modifier.fillMaxSize(),
                    floatingActionButton = {
                        Log.d("Route", navController.currentBackStackEntry?.destination?.route.toString())
                        Log.d("Route", NavigationClasses.NavigationRoutes.Login.nroute)
                        if (currentRoute != NavigationClasses.NavigationRoutes.Login.nroute)

                            FloatingButtonClean(productVM)

                    }

                ) { innerPadding ->
                    Navigation(productVM, navController, updateCurrentRoute = {
                        currentRoute = it
                        Log.d("Route dd", currentRoute.toString())
                    })
                    //Log.d("Route", navController.currentBackStackEntry?.destination?.route.toString())
                }
            }
        }
    }
}

@Composable
fun FloatingButtonClean(productVM: ProductsListVM = viewModel(), modifier: Modifier = Modifier) {

    FloatingActionButton(
        onClick = {
            Log.d("Route", "FloatingButtonClean")
            productVM.clearList()
        },
        modifier = modifier
    ) {
        Text(text = "X")
    }

}
