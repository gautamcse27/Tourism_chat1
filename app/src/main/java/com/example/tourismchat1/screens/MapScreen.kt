package com.example.tourismchat1.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import androidx.navigation.NavHostController
import com.example.tourismchat1.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(navController: NavHostController) {
    Scaffold(topBar = { TopAppBar(title = { Text("Explore Bhojpur") }) }) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Display Bhojpur District Image
            AsyncImage(
                model = "https://upload.wikimedia.org/wikipedia/commons/4/4c/Veer_Kunwar_Singh_Fort_Ara_Bihar.jpg",
                contentDescription = "Bhojpur District View",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Discover Bhojpur, Bihar",
                fontSize = 22.sp,
                color = Color(0xFF004D40) // Dark Green
            )

            Text(
                text = "Famous for its historical sites and cultural heritage!",
                fontSize = 16.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Back Button
            Button(
                onClick = { navController.popBackStack() },
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Back to Home")
            }
        }
    }
}
