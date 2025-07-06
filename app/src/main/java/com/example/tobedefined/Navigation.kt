package com.example.tobedefined

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import com.example.tobedefined.Dashboard.DashboardUI
import com.example.tobedefined.Login.LoginUI
import com.example.tobedefined.ProductsList.ProductsListUI
import com.example.tobedefined.common.data.Product
import com.example.tobedefined.common.modules.NavigationClasses

@Composable
fun Navigation(navHostController: NavHostController = rememberNavController(), modifier: Modifier = Modifier) {

    val navGraph = navHostController.createGraph(
        startDestination = NavigationClasses.NavigationRoutes.Login.nroute
    ) {
        composable(route = NavigationClasses.NavigationRoutes.Login.nroute ) {
            LoginUI(navHostController)
        }

        composable(route = NavigationClasses.NavigationRoutes.Dashboard.nroute) {
            DashboardUI()
        }

        composable(route = NavigationClasses.NavigationRoutes.ProductsList.nroute) {
            ProductsListUI()
        }

    }

    NavHost(
        navController = navHostController,
        graph = navGraph,
        modifier = modifier
    )
}


val productSeed: List<Product> = listOf(
    Product(0, "Coca-Cola", 2.5, image = R.drawable.cocacola),
    Product(1, "Sumol", 2.5),
    Product(2, "Vodka Redbull", 3.5),
    Product(3, "Cachorros", 2.5, R.drawable.hotdog),
    Product(4, "Coca-Cola", 2.5, image = R.drawable.cocacola),
    Product(5, "Sumol", 2.5),
    Product(6, "Vodka Redbull", 3.5),
    Product(7, "Cachorros", 2.5, R.drawable.hotdog),
            Product(8, "Sumol 2", 2.5),
Product(9, "Vodka Redbull 2", 3.5),
Product(10, "Cachorros 2", 2.5, R.drawable.hotdog)

)