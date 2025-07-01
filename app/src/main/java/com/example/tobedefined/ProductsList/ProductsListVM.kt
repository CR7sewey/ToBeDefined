package com.example.tobedefined.ProductsList

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tobedefined.common.data.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductsListVM : ViewModel() {

    private val _productsList = MutableStateFlow<List<Product>>(emptyList())
    var productListUI: StateFlow<List<Product>> = _productsList


    fun updateList(prod: Product) {
        viewModelScope.launch(Dispatchers.IO) {
        try {
            var p = _productsList.value.find { it2 -> prod.id == it2.id }
            if (p != null) {

                var tempList  = _productsList.value.map { it.copy()
                    if (it.id == p.id) {
                        it.quantity = it.quantity?.plus(1)
                    }
                    it
                }

                _productsList.value = tempList
            }
            else {
                _productsList.value = _productsList.value + prod
            }
        }
        catch (ex: Exception) {
            Log.d("ERRO", ex.message.toString())
        }
    }
    }

}