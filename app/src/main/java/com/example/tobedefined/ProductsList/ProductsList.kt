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
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun ProductsListUI(modifier: Modifier = Modifier) {
//
    //var productsChosen by remember { mutableListOf<Product>() }
    var prodVM = viewModel<ProductsListVM>()
    //var productsChosen = prodVM.productListUI.collectAsState().value
    //var productsChosen = remember { mutableListOf<Product>() }
    Row(
        modifier = Modifier

    ) {
        DisplayProductsChosen(productsListVM = prodVM)

        Spacer(modifier = Modifier.width(10.dp))
        val scroll = rememberScrollState(0)

        Column(
            modifier = Modifier
                .verticalScroll(scroll)
                .fillMaxWidth()
        ) {
            ProductsList(productsListVM = prodVM, "First", onAddProduct = { it ->
                //productsChosen.add(it)
                prodVM.updateList(it)
            })
            ProductsList(productsListVM = prodVM, "Second", onAddProduct = { it ->
                //productsChosen.add(it)
                prodVM.updateList(it)
            })
        }

        //
    }
}

@Composable
fun DisplayProductsChosen(productsListVM: ProductsListVM, modifier: Modifier = Modifier) {
    var productsChosen = productsListVM.productListUI.collectAsState().value
    Column(
        modifier = Modifier.width(256.dp).fillMaxHeight()
            .background(color = Color.LightGray).padding(8.dp),
        verticalArrangement = Arrangement.SpaceBetween

    ) {
        // Product

        // quantity x price

        LazyColumn {
            items(productsChosen) { it ->
                Text(
                    text = it.name,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "${it.quantity}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = "${it.price} €",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(56.dp))

        Row(
            modifier = modifier.fillMaxWidth(),
            //verticalAlignment = Alignment.Bottom,
            //horizontalArrangement = Arrangement.Absolute.SpaceBetween
        ) {
            Text(text = "Total: ",
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,

            )
            Spacer(modifier = Modifier.weight(1f)) // Spacer takes up all available space
            Text(text = "${productsListVM.total.collectAsState().value}",
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,

                )
        }

    }
}

@Composable
fun ProductsList(productsListVM: ProductsListVM, label: String, onAddProduct: (Product) -> Unit , modifier: Modifier = Modifier) {

    val scroll = rememberScrollState(0)

    Column(
        modifier = Modifier
            .fillMaxWidth().padding(18.dp)
    ) {

        Text(
            label,
            fontWeight = FontWeight.SemiBold,
            fontSize = 36.sp
        )

        LazyRow {
            items(productSeed) { it ->
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

        Spacer(modifier = Modifier.height(2.dp))

    }
}