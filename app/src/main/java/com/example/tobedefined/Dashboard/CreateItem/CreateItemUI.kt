package com.example.tobedefined.Dashboard.CreateItem

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.tobedefined.Login.TextInput
import com.example.tobedefined.ProductsList.ProductsListVM
import com.example.tobedefined.common.data.Category
import com.example.tobedefined.common.data.Product
import com.example.tobedefined.common.modules.NavigationClasses
import com.example.tobedefined.productSeed

@Composable
fun CreateItemUI(navHostController: NavHostController, productVM: ProductsListVM, id: String = "", modifier: Modifier = Modifier) {



    if (id != "") {
        val product = productSeed.find { it.id == id }
        ProductInputForm(navHostController, product) // method to insert in database/firebase etc to be done later on
    }
    else {

        ProductInputForm(navHostController) // method to insert in database/firebase etc to be done later on
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductInputForm(

    navHostController: NavHostController = NavHostController(LocalContext.current),
    product: Product? = null,
    categories: List<Category> = Category.entries, // Get categories from your enum
    onSubmit: (product: Product) -> Unit = { Log.d("A","A") },
    modifier: Modifier = Modifier,
) {
    var name by remember { mutableStateOf(product?.name ?: "") }
    var price by remember { mutableStateOf(product?.price?.toString() ?: "") }
    var selectedCategory by remember { mutableStateOf(product?.category ?: categories.firstOrNull()) } // Default to first or null
    var quantity by remember { mutableStateOf((product?.quantity ?: "1").toString()) } // Quantity as String for TextField

    var nameError by remember { mutableStateOf<String?>(null) }
    var priceError by remember { mutableStateOf<String?>(null) }
    var quantityError by remember { mutableStateOf<String?>(null) }
    var categoryError by remember { mutableStateOf<String?>(null)}

    var isCategoryExpanded by remember { mutableStateOf(false) }

    fun validateFields(): Boolean {
        nameError = if (name.isBlank()) "Name cannot be empty" else null
        priceError = if (price.isBlank() || price.toDoubleOrNull() == null || price.toDouble() <= 0) "Enter a valid price" else null
        quantityError = if (quantity.isBlank() || quantity.toIntOrNull() == null || quantity.toInt() <= 0) "Enter a valid quantity" else null
        categoryError = if (selectedCategory == null) "Please select a category" else null

        return nameError == null && priceError == null && quantityError == null && categoryError == null
    }

    Column(
        modifier = modifier
            .padding(64.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp) // Adds space between items
    ) {
        Text("Add New Product", style = MaterialTheme.typography.headlineSmall)

        OutlinedTextField(
            value = name,
            onValueChange = { name = it; nameError = null },
            label = { Text("Product Name") },
            singleLine = true,
            isError = nameError != null,
            supportingText = { if (nameError != null) Text(nameError!!) },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = price,
            onValueChange = { price = it; priceError = null },
            label = { Text("Price (€)") },
            singleLine = true,
            isError = priceError != null,
            supportingText = { if (priceError != null) Text(priceError!!) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )

        // Category Dropdown (ExposedDropdownMenuBox)
        ExposedDropdownMenuBox(
            expanded = isCategoryExpanded,
            onExpandedChange = { isCategoryExpanded = !isCategoryExpanded },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = selectedCategory?.name ?: "Select Category",
                onValueChange = { /* Read only */ },
                label = { Text("Category") },
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isCategoryExpanded) },
                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                modifier = Modifier
                    .menuAnchor() // Important for connecting the TextField to the DropdownMenu
                    .fillMaxWidth(),
                isError = categoryError != null,
                supportingText = { if (categoryError != null) Text(categoryError!!) }
            )
            ExposedDropdownMenu(
                expanded = isCategoryExpanded,
                onDismissRequest = { isCategoryExpanded = false }
            ) {
                categories.forEach { category ->
                    DropdownMenuItem(
                        text = { Text(category.name) },
                        onClick = {
                            selectedCategory = category
                            categoryError = null
                            isCategoryExpanded = false
                        }
                    )
                }
            }
        }

        OutlinedTextField(
            value = quantity,
            onValueChange = { quantity = it; quantityError = null },
            label = { Text("Quantity") },
            singleLine = true,
            isError = quantityError != null,
            supportingText = { if (quantityError != null) Text(quantityError!!) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done // Last field
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Button(
                onClick = {
                    if (product != null) {
                        // TODO
                        productSeed.remove(product)
                        navHostController.popBackStack()
                    }
                    else {
                        // TODO
                        navHostController.popBackStack()
                    }


                          },
                modifier = Modifier.width(256.dp),
                shape = MaterialTheme.shapes.medium, // Rounded corners
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.inversePrimary
                )
            ) {
                val texto = if (product != null) "Delete" else "Back"
                Text(texto, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onPrimary)
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = {


                        if (validateFields() && selectedCategory != null) {
                            val newProduct = Product(
                                id = product?.id ?: (0..Int.MAX_VALUE).random().toString(), //product?.id ?: (0..Int.MAX_VALUE).random(), // Example ID generation
                                name = name.trim(),
                                category = selectedCategory!!, // Safe due to validation
                                price = price.toDouble(),      // Safe due to validation
                                quantity = quantity.toInt()    // Safe due to validation
                            )
                            // Optionally clear fields after submission
                            name = ""
                            price = ""
                            // selectedCategory = categories.firstOrNull() // Or keep it
                            quantity = "1"

                            if (product != null) {
                                val p1 = productSeed.find { it -> it.id == product.id }
                                val i1 = productSeed.indexOf(p1)
                                productSeed.removeAt(i1)
                                productSeed.add(i1, newProduct)
                                // TODO
                            }
                            else {
                                // TODO
                                productSeed.add(newProduct)

                            }
                            navHostController.navigate(NavigationClasses.NavigationRoutes.ProductsList.nroute)
                        }




                },
                modifier = Modifier.width(256.dp),
                shape = MaterialTheme.shapes.medium // Rounded corners
            ) {
                val texto = if (product != null) "Update Product" else "Create Product"

                Text(texto, style = MaterialTheme.typography.titleMedium)
            }
        }


    }
}
