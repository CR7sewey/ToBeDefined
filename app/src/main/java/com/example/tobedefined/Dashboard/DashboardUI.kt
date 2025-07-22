package com.example.tobedefined.Dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tobedefined.itemsL

@Composable
fun DashboardUI(modifier: Modifier = Modifier) {
    GridContent()
}

data class TempItem(
    val id: Int,
    val name: String,
    val imageUrl: Int
)



@Composable
fun GridContent(modifier: Modifier = Modifier) {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LazyVerticalGrid(
            modifier = modifier.width(520.dp),
            columns = androidx.compose.foundation.lazy.grid.GridCells.Fixed(1),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(64.dp),
            horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(16.dp),
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(16.dp)
        ) {
            items(itemsL) { index ->
                androidx.compose.material3.Card(
                    modifier = Modifier.fillMaxSize(),
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp),
                    elevation = androidx.compose.material3.CardDefaults.cardElevation(4.dp)
                ) {
                    Column {
                       /* Image(
                            painter = androidx.compose.ui.res.painterResource(id = index.imageUrl),
                            contentDescription = "Item Icon",
                            modifier = Modifier
                                .size(160.dp)
                                .padding(16.dp),
                            alignment = Alignment.Center,
                            contentScale = androidx.compose.ui.layout.ContentScale.Crop
                        )*/
                        // Display the index or name of the item
                        Text(
                            text = index.name,
                            modifier = Modifier.padding(16.dp).align(Alignment.CenterHorizontally),
                            style = androidx.compose.material3.MaterialTheme.typography.bodyLarge,
                        )

                    }
                }
            }

        }
    }
}
