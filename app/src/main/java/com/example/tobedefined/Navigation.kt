package com.example.tobedefined

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import com.example.tobedefined.Dashboard.CreateItem.CreateItemUI
import com.example.tobedefined.Dashboard.DashboardUI
import com.example.tobedefined.Dashboard.TempItem
import com.example.tobedefined.Login.LoginUI
import com.example.tobedefined.ProductsList.ProductsListUI
import com.example.tobedefined.ProductsList.ProductsListVM
import com.example.tobedefined.common.data.Category
import com.example.tobedefined.common.data.Product
import com.example.tobedefined.common.modules.NavigationClasses

@Composable
fun Navigation(productVM: ProductsListVM, navHostController: NavHostController = rememberNavController(), updateCurrentRoute: (String?) -> Unit, modifier: Modifier = Modifier) {

    val navGraph = navHostController.createGraph(
        startDestination = NavigationClasses.NavigationRoutes.Login.nroute
    ) {
        composable(route = NavigationClasses.NavigationRoutes.Login.nroute ) {
            LoginUI(navHostController)

            updateCurrentRoute.invoke(navHostController.currentBackStackEntry?.destination?.route)
            Log.d("Route d", navHostController.currentBackStackEntry?.destination?.route.toString())

        }

        composable(route = NavigationClasses.NavigationRoutes.Dashboard.nroute) {
            DashboardUI(navHostController)
            updateCurrentRoute(navHostController.currentBackStackEntry?.destination?.route)
            Log.d("Route d", navHostController.currentBackStackEntry?.destination?.route.toString())

        }

        composable(route = NavigationClasses.NavigationRoutes.ProductsList.nroute) {
            ProductsListUI(productVM)
            updateCurrentRoute(navHostController.currentBackStackEntry?.destination?.route)
        }

        composable(route = NavigationClasses.NavigationRoutes.CreateItem.nroute) {
            CreateItemUI(navHostController)
            updateCurrentRoute(navHostController.currentBackStackEntry?.destination?.route)
        }

    }

    NavHost(
        navController = navHostController,
        graph = navGraph,
        modifier = modifier
    )
}

/**
 * data class Product(
 *     val id: Int,
 *     val name: String,
 *     val category: String,
 *     val price: Double,
 *     var quantity: Int = 1,
 *     val image: Int? = null,
 *     val description: String? = null
 * )
 */


val productSeed: List<Product> = listOf(
    Product(id = 0, name = "Coca-Cola", category = Category.Sumos, price = 3.0),
    Product(id = 1, name = "Pepsi", category = Category.Sumos, price = 3.0),
    Product(id = 2, name = "Sumol", category = Category.Sumos, price = 3.0),
    Product(id = 3, name = "Ice Tea", category = Category.Sumos, price = 3.0),
    Product(id = 4, name = "Cerveja", category = Category.Alcool, price = 1.0),
    Product(id = 5, name = "Vodka", category = Category.Alcool, price = 2.5),
    Product(id = 6, name = "Whisky", category = Category.Alcool, price = 3.0),
    Product(id = 7, name = "Shot", category = Category.Alcool, price = 1.5),
    Product(id = 8, name = "Cachorro", category = Category.Comida, price = 2.5),
    Product(id = 9, name = "Bifana", category = Category.Comida, price = 2.5),
    Product(id = 10, name = "Pão Chouriço", category = Category.Comida, price = 2.5)
)

val itemsL = listOf<TempItem>(
    TempItem(1, "Create Item", R.drawable.cocacola, "dashboard/createItem"),
    TempItem(2, "Delete/Edit Item", R.drawable.hotdog, "dashboard/editItem"),
    TempItem(3, "Stats", R.drawable.hotdog),
)