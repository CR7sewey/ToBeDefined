package com.example.tobedefined.ProductsList.data

import com.example.tobedefined.common.data.Product
import com.google.firebase.auth.FirebaseAuth

class ProductsListRepository: IProductsListRepository {

    private var productsListService: ProductsListService = ProductsListService()

    override suspend fun getProducts(): List<Product> {
        // FIREBASE REALTIME DATABASE
        return productsListService.getProducts()

    }

}