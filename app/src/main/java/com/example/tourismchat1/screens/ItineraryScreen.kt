package com.example.tourismapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItineraryScreen(navController: NavHostController) {
    Scaffold(topBar = { TopAppBar(title = { Text("Itinerary Planner") }) }) {
        Column(
            modifier = Modifier.padding(it).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Plan Your Trip", fontSize = 20.sp, color = Color.Blue)
            Button(onClick = { navController.popBackStack() }, shape = RoundedCornerShape(10.dp)) {
                Text("Back")
            }
        }
    }
}
