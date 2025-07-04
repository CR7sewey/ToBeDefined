package com.example.tobedefined.common.data

data class Product(
    val id: Int,
    val name: String,
    val price: Double,
    val image: Int? = null,
    var quantity: Int? = 1
)

val productSeed: List<Product> = listOf(
    Product(0, "Coca-Cola", 2.5),
    Product(1, "Sumol", 2.5),
    Product(2, "Vodka Redbull", 3.5),
    Product(3, "Cachorros", 2.5)

)