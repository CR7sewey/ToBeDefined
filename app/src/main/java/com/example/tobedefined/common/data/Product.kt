package com.example.tobedefined.common.data

data class Product(
    val id: String = "0",
    val name: String = "",
    val category: Category = Category.UNKNOWN,
    val price: Double = 0.0,
    var quantity: Int = 1,
    //val image: Int? = null,
    //val description: String? = null
)

data class CategoryData(
    var id: String = "",
    var name: String = "" // e.g., "Electronics"
    // other category-specific fields
)

enum class Category {
    Sumos, Comida, Alcool, UNKNOWN
}