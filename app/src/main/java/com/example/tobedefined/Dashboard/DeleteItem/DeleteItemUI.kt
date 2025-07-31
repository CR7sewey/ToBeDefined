package com.example.tobedefined.Dashboard.DeleteItem

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.tobedefined.ProductsList.DisplayProductsChosen
import com.example.tobedefined.ProductsList.ProductsList
import com.example.tobedefined.ProductsList.ProductsListUI
import com.example.tobedefined.ProductsList.ProductsListVM
import com.example.tobedefined.common.data.Category

@Composable
fun DeleteItemUI(navHostController: NavHostController, productsListVM: ProductsListVM, modifier: Modifier = Modifier) {

    Row(
        modifier = Modifier

    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            items(Category.entries) { it ->
                ProductsList(productsListVM = productsListVM, it, onAddProduct = { it ->
                    //productsChosen.add(it)
                    navHostController.navigate("dashboard/editItem/${it.id}")
                })
            }
        }

        //
    }
}