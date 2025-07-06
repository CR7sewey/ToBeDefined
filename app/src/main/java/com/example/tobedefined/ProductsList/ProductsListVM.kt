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
    var total: StateFlow<Double> = _total


    fun updateList(prod: Product) {
        viewModelScope.launch(Dispatchers.IO) {
        try {
            var p = _productsList.value.find { it2 -> prod.id == it2.id }
            if (p != null) {

                var tempList = _productsList.value.map { existingProduct ->
                    if (existingProduct.id == p.id) {
                        // You need to create a NEW Product object when updating quantity
                        existingProduct.copy(quantity = existingProduct.quantity + 1)
                    } else {
                        existingProduct // Return the original object if not modified
                    }
                }
                _productsList.value = tempList
                //_productsList.value = _productsList.value.dropLast(1)
                Log.d("AAAA", _productsList.value.toString())
            }
            else {
                _productsList.value = _productsList.value + prod.copy(quantity = 1)

            }
            calculateTotal()
        }
        catch (ex: Exception) {
            Log.d("ERRO", ex.message.toString())
        }
    }
    }

    private fun calculateTotal() {
        _total.value = _productsList.value.sumOf { (it.price * it.quantity.toDouble()) }
    }

    fun clearList() {
        viewModelScope.launch {
            Log.d("AAAA", "Clearing list. Current list: ${_productsList.value}")
            _productsList.value = emptyList() // This is the correct way to clear and notify observers
            _total.value = 0.0             // This is correct
            Log.d("AAAA", "List cleared. New list: ${_productsList.value}, New total: ${_total.value}")

        }

    }

}