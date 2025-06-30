package com.example.tobedefined.ProductsList

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.tobedefined.common.data.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ProductsListVM : ViewModel() {

    private val productsList = MutableStateFlow<List<Product>>(emptyList())
    var productListUI: StateFlow<List<Product>> = productsList


    fun updateList(prod: Product) {
        try {
            productsList.value = productsList.value + prod
        }
        catch (ex: Exception) {
            Log.d("ERRO", ex.message.toString())
        }
    }

}