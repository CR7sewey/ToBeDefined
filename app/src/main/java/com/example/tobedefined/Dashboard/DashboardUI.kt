package com.example.tobedefined.Dashboard

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DashboardUI(modifier: Modifier = Modifier) {
    GridContent()
}

@Composable
fun GridContent(modifier: Modifier = Modifier) {

    LazyVerticalGrid(
        modifier = modifier,
        columns = androidx.compose.foundation.lazy.grid.GridCells.Fixed(4),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(64.dp),
        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(16.dp),
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(16.dp)
    ) {
        items(20) { index ->
            androidx.compose.material3.Card(
                modifier = Modifier.fillMaxSize(),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp),
                elevation = androidx.compose.material3.CardDefaults.cardElevation(4.dp)
            ) {
                Text(
                    text = "Item $index",
                    modifier = Modifier.padding(16.dp),
                    style = androidx.compose.material3.MaterialTheme.typography.bodyLarge
                )
            }
        }

    }

}
