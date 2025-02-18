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
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.example.tourismchat1.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttractionsScreen(navController: NavHostController) {
    val attractions = remember { mutableStateListOf<TouristPlace>() }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    // Fetch attractions from API
    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            try {
                val response = RetrofitInstance.api.getAttractions()
                attractions.addAll(response)
                isLoading = false
            } catch (e: Exception) {
                errorMessage = "Error fetching attractions: ${e.message}"
                isLoading = false
            }
        }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Attractions") }) },
                bottomBar = {
            Box(modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Back")
                }
            }
        }
    ) {
        paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        )
        {
            when {
                isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                }
                errorMessage != null -> {
                    Text(
                        text = errorMessage ?: "",
                        color = Color.Red,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                else -> {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(attractions) { place ->
                            Card(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .fillMaxWidth(),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    // Display a default image since the API doesn't provide one
                                    AsyncImage(
                                        model = place.image_url, // Remote image URL
                                        contentDescription = place.name,
                                        placeholder = painterResource(id = R.drawable.tourism_logo), // While loading
                                        error = painterResource(id = R.drawable.tourism_logo), // If error occurs
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(200.dp)
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(text = place.name, fontSize = 20.sp, color = Color.Blue)
                                    Text(text = place.location, fontSize = 16.sp)
                                    Text(text = place.description, fontSize = 14.sp)
                                }
                            }
                        }
                    }
                }
            }


        }
    }
}
