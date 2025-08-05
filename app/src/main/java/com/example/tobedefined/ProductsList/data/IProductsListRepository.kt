package com.example.tobedefined.ProductsList.data

import com.example.tobedefined.common.data.Product

interface IProductsListRepository {
    suspend fun getProducts(): List<Product>
}