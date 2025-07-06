package com.example.tobedefined.ProductsList

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tobedefined.R
import com.example.tobedefined.common.data.Product
import com.example.tobedefined.productSeed
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.LineBreak
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tobedefined.common.data.Category


@Composable
fun ProductsListUI(productsListVM: ProductsListVM, modifier: Modifier = Modifier) {
//
    //var productsChosen by remember { mutableListOf<Product>() }
    //var prodVM = viewModel<ProductsListVM>()
    //var productsChosen = prodVM.productListUI.collectAsState().value
    //var productsChosen = remember { mutableListOf<Product>() }
    Row(
        modifier = Modifier

    ) {
        DisplayProductsChosen(productsListVM = productsListVM)

        Spacer(modifier = Modifier.width(10.dp))
        val scroll = rememberScrollState(0)

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            items(Category.entries) { it ->
                ProductsList(productsListVM = productsListVM, it, onAddProduct = { it ->
                    //productsChosen.add(it)
                    productsListVM.updateList(it)
                })
            }
        }

        //
    }
}

@Composable
fun DisplayProductsChosen(productsListVM: ProductsListVM, modifier: Modifier = Modifier) {
    var productsChosen = productsListVM.productListUI.collectAsState().value
    Column(
        modifier = Modifier.width(256.dp).fillMaxHeight()
            .background(color = Color.LightGray).padding(18.dp),
        verticalArrangement = Arrangement.SpaceBetween

    ) {
        // Product

        // quantity x price

        LazyColumn(
            modifier = Modifier.weight(1f) // Crucial: This makes LazyColumn take available space
        ) {
            items(productsChosen) { it ->
                Text(
                    text = it.name,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = "${it.quantity}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = "x ${it.price} €",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                // Add a Divider after each item, except maybe the last one
                if (productsChosen.last() != it) { // Optional: Don't add divider after the last item
                    Divider(
                        color = Color.Gray, // Customize color as needed
                        thickness = 1.dp,  // Customize thickness as needed
                        modifier = Modifier.padding(vertical = 8.dp) // Optional padding around the divider
                    )
                }
            }
        }

        //Spacer(modifier = Modifier.height(56.dp))
// Spacer to ensure there's some gap if the list is short,
        // or if you want to push the Total row down a bit more.
        // This is optional if the LazyColumn with weight(1f) behaves as desired.
        // Spacer(modifier = Modifier.height(8.dp))

        // Total row - always at the bottom
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween // Keep this for text alignment
            //verticalAlignment = Alignment.Bottom,
            //horizontalArrangement = Arrangement.Absolute.SpaceBetween
        ) {
            Text(text = "Total: ",
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,

            )
            Spacer(modifier = Modifier.weight(1f)) // Spacer takes up all available space
            Text(text = "${productsListVM.total.collectAsState().value} €",
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,

                )
        }

    }
}

@Composable
fun ProductsList(productsListVM: ProductsListVM, cat: Category, onAddProduct: (Product) -> Unit , modifier: Modifier = Modifier) {

    val scroll = rememberScrollState(0)

    Column(
        modifier = Modifier
            .fillMaxWidth().padding(18.dp)
    ) {

        Text(
            cat.name,
            fontWeight = FontWeight.SemiBold,
            fontSize = 36.sp
        )

        LazyRow {
            items(productSeed) { it ->
                if (it.category == cat) {
                    Card(
                        colors = CardDefaults.cardColors(),
                        modifier = Modifier
                            .padding(4.dp)
                            .fillMaxWidth().clickable(onClick = {
                                onAddProduct(it)
                            }),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    ) {
                        Text(
                            text = it.name,
                            fontWeight = FontWeight.Bold,
                            fontSize = 30.sp,
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(16.dp)
                        )
                        Text(
                            text = "${it.price} €",
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp,
                            color = Color.DarkGray,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))

                }

            }

        }

        Spacer(modifier = Modifier.height(2.dp))

    }
}