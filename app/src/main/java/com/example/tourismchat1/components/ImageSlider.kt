package com.example.tourismchat1.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.tourismchat1.R
import com.google.accompanist.pager.*

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ImageSlider(imageUrls: List<String>) {
    val pagerState = rememberPagerState()

    Column {
        HorizontalPager(
            count = imageUrls.size,
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        ) { page ->
            AsyncImage(
                model = imageUrls[page],
                contentDescription = "Tourist Image",
                placeholder = painterResource(id = R.drawable.tourism_logo),
                error = painterResource(id = R.drawable.tourism_logo),
                modifier = Modifier.fillMaxSize()
            )
        }

        // Dots Indicator
        Row(
            Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(imageUrls.size) { index ->
                val color = if (pagerState.currentPage == index) Color.Blue else Color.Gray
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .size(8.dp)
                        .background(color, shape = RoundedCornerShape(50))
                )
            }
        }
    }
}
