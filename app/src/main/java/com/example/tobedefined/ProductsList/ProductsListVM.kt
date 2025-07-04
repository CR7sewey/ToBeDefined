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

    private val _total = MutableStateFlow<Double>(0.0)
    var total = _total


    fun updateList(prod: Product) {
        viewModelScope.launch(Dispatchers.IO) {
        try {
            var p = _productsList.value.find { it2 -> prod.id == it2.id }
            if (p != null) {

                var tempList  = _productsList.value.map { it.copy()
                    if (it.id == p.id) {
                        it.quantity = it.quantity?.plus(1)
                    }
                    Product(it.id, it.name, it.price, it.image, it.quantity)
                }
                val currentSize = _productsList.value.size
               // _productsList.value = tempList.plus(Product(9999999,"",0.0))
               // _productsList.value = _productsList.value.subList(0, currentSize-1)
                _productsList.value = tempList
                //_productsList.value = _productsList.value.dropLast(1)
                Log.d("AAAA", _productsList.value.toString())
            }
            else {
                _productsList.value = _productsList.value + prod

            }
            calculateTotal()
        }
        catch (ex: Exception) {
            Log.d("ERRO", ex.message.toString())
        }
    }
    }

    private fun calculateTotal() {
        total.value = _productsList.value.sumOf { (it.price * (it.quantity?.toDouble() ?: 0.0)) }
    }

}