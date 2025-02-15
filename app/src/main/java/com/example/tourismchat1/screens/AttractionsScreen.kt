package com.example.tourismapp.ui.screens
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

import com.example.tourismchat1.data.models.TouristPlace
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttractionsScreen(navController: NavHostController) {
    val attractions = remember { mutableStateListOf<TouristPlace>() }

    // Fetch attractions from API
    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            try {
                val response = RetrofitInstance.api.getAttractions()
                attractions.addAll(response)
            } catch (e: Exception) {
                println("Error fetching attractions: ${e.message}")
            }
        }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Attractions") }) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(attractions) { place ->
                    Card(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = place.name, fontSize = 20.sp, color = Color.Blue)
                            Text(text = place.location, fontSize = 16.sp)
                            Text(text = place.description, fontSize = 14.sp)
                        }
                    }
                }
            }

            Button(
                onClick = { navController.popBackStack() },
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text("Back")
            }
        }
    }
}
