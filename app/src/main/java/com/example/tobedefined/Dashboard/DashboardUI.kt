package com.example.tobedefined.Dashboard

import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.tobedefined.itemsL
import kotlinx.coroutines.delay


@Composable
fun DashboardUI(navHostController: NavHostController, modifier: Modifier = Modifier) {
    GridContent(navHostController)
}

data class TempItem(
    val id: Int,
    val name: String,
    val imageUrl: Int
)



@Composable
fun GridContent(navHostController: NavHostController? = null, modifier: Modifier = Modifier) {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(64.dp),
            horizontalAlignment = Alignment.CenterHorizontally
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
                                modifier = Modifier
                                    .padding(16.dp)
                                    .align(Alignment.CenterHorizontally),
                                style = androidx.compose.material3.MaterialTheme.typography.bodyLarge,
                            )

                        }
                    }
                }



            }
            Spacer(modifier = Modifier.height(26.dp))

            val interactionSource = remember { MutableInteractionSource() }
            val isPressed = interactionSource.collectIsPressedAsState()
            //val pressedListener = rememberUpdatedState(onClick)
            val stepDelay = 100L

            LaunchedEffect(isPressed) {
                while (isPressed.value) {
                    delay(stepDelay.coerceIn(1L, Long.MAX_VALUE))
                    //pressedListener()
                }
            }

            IconButton(
                modifier = modifier,
                onClick = { navHostController?.popBackStack() },
                interactionSource = interactionSource
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = "Previous",
                    modifier = Modifier.size(48.dp)
                )
            }
        }
    }
}
