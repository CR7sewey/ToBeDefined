package com.example.tobedefined.common.data

data class Product(
    val id: Int,
    val name: String,
    val category: Category,
    val price: Double,
    var quantity: Int = 1,
    val image: Int? = null,
    val description: String? = null
)

enum class Category {
    Sumos, Comida, Alcool
}