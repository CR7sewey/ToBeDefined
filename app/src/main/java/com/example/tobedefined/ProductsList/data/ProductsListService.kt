package com.example.tobedefined.ProductsList.data

import android.util.Log
import androidx.compose.ui.geometry.isEmpty
import com.example.tobedefined.common.data.Product // Your Product data class
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await


class ProductsListService {

    // Get a Cloud Firestore instance
    // It's good practice to initialize it once, either here or via dependency injection
    private val db: FirebaseFirestore = Firebase.firestore

    companion object {
        private const val PRODUCTS_COLLECTION_NAME = "Products"
        private const val TAG = "ProductsListService"
    }

    suspend fun getProducts(): List<Product> {
        val productList = mutableListOf<Product>()
        try {
            val querySnapshot = db.collection(PRODUCTS_COLLECTION_NAME)
                .get()  // Perform the get() call
                .await() // Suspend until the task is complete

            if (querySnapshot.isEmpty) {
                Log.d(TAG, "No products found in '$PRODUCTS_COLLECTION_NAME' collection.")
                return emptyList() // Return an empty list if no documents are found
            }

            for (document in querySnapshot.documents) {
                // Option 1: Using toObject<Product>() - Recommended if Product is a simple data class
                // This requires your Product data class to have an empty constructor
                // or default values for all properties.
                // Also, field names in Firestore must match property names in Product.
                val product = document.toObject(Product::class.java)
                if (product != null) {
                    // If your Product class needs the document ID, you can assign it here
                    // Assuming your Product data class has an 'id' field of type String
                    // val productWithId = product.copy(id = document.id)
                    // productList.add(productWithId)
                    productList.add(product) // Add without ID if not needed or handled differently
                    Log.d(TAG, "Fetched product: ${document.id} => ${document.data}")
                } else {
                    Log.w(TAG, "Failed to convert document ${document.id} to Product object.")
                }

                // Option 2: Manual Mapping (if toObject fails or you need more control)
                /*
                try {
                    val name = document.getString("name") ?: "" // Provide default or handle missing field
                    val price = document.getDouble("price") ?: 0.0
                    val quantity = document.getLong("quantity")?.toInt() ?: 0 // Firestore stores numbers as Long
                    val categoryString = document.getString("category") // Assuming category is stored as String

                    // Convert categoryString to Category enum (example)
                    val category = try {
                        categoryString?.let { com.example.tobedefined.common.data.Category.valueOf(it) }
                                    ?: com.example.tobedefined.common.data.Category.Sumos // Default category
                    } catch (e: IllegalArgumentException) {
                        Log.w(TAG, "Invalid category string: $categoryString for document ${document.id}")
                        com.example.tobedefined.common.data.Category.Sumos // Default on error
                    }

                    // Assuming your Product constructor or factory method
                    // You might need to adjust this based on your Product class definition
                    // And ensure image and description are handled if they exist in Firestore
                    val product = Product(
                        id = document.id, // Using Firestore document ID as the product ID
                        name = name,
                        price = price,
                        quantity = quantity,
                        category = category
                        // image = document.getLong("image")?.toInt(), // Example if image is a number
                        // description = document.getString("description")
                    )
                    productList.add(product)
                    Log.d(TAG, "Fetched product (manual): ${document.id} => ${document.data}")
                } catch (e: Exception) {
                    Log.e(TAG, "Error mapping document ${document.id}: ", e)
                }
                */
            }
            Log.d(TAG, "Successfully fetched ${productList.size} products.")
        } catch (e: Exception) {
            // Handle exceptions, e.g., network errors, permissions issues
            Log.e(TAG, "Error fetching products: ", e)
            // Depending on your error handling strategy, you might rethrow,
            // return an empty list, or return a custom error state.
            return emptyList() // Return empty list on error for simplicity here
        }
        return productList
    }

    // You can add other service methods here, e.g., addProduct, updateProduct, deleteProduct
}
