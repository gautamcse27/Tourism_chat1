package com.example.tourismchat1.screens

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.tourismchat1.R
import com.example.tourismchat1.components.ImageSlider
import com.example.tourismchat1.data.models.TouristPlace
import com.example.tourismchat1.network.RetrofitInstance
import com.google.accompanist.pager.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
@Composable
fun AttractionDetailsScreen(navController: NavHostController, id: String?) {
    var attraction by remember { mutableStateOf<TouristPlace?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current

    LaunchedEffect(id) {
        id?.toIntOrNull()?.let { intId ->
            withContext(Dispatchers.IO) {
                try {
                    val response = RetrofitInstance.api.getAttractionDetails(intId)
                    attraction = response
                    isLoading = false
                } catch (e: Exception) {
                    errorMessage = "Error fetching details: ${e.message}"
                    isLoading = false
                    Log.e("Error", "Failed to load attraction: ${e.message}")
                }
            }
        } ?: run {
            errorMessage = "Invalid attraction ID"
            isLoading = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = attraction?.name ?: "Loading...",
                        fontSize = 20.sp,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { shareAttraction(context, attraction) }) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_share_24),
                            contentDescription = "Share",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1976D2) // Set a blue background for visibility
                )
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)) {
            when {
                isLoading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                errorMessage != null -> {
                    Text(
                        text = errorMessage ?: "",
                        color = Color.Red,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                attraction != null -> {
                    Column(modifier = Modifier.fillMaxSize()) {
                        attraction?.image_urls?.let { images ->
                            if (images.isNotEmpty()) {
                                ImageSlider(imageUrls = images)
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))



                        Text(
                            text = "Location: ${attraction?.location}",
                            fontSize = 18.sp,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = attraction?.description ?: "",
                            fontSize = 16.sp,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // ✅ Google Map with Latitude & Longitude
                        attraction?.let { place ->
                            val location = LatLng(place.latitude, place.longitude)
                            val cameraPositionState = rememberCameraPositionState()

                            LaunchedEffect(location) {
                                cameraPositionState.move(CameraUpdateFactory.newLatLngZoom(location, 12f))
                            }

                            GoogleMap(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(250.dp)
                                    .padding(16.dp),
                                cameraPositionState = cameraPositionState
                            ) {
                                Marker(
                                    state = rememberMarkerState(position = location),
                                    title = place.name ?: "Unknown Place"
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

// ✅ Function to Share Attraction Details (Includes Google Maps Link)
private fun shareAttraction(context: Context, attraction: TouristPlace?) {
    attraction?.let {
        val mapsUrl = "https://www.google.com/maps/search/?api=1&query=${it.latitude},${it.longitude}"
        val shareText = """
            🌍 *${it.name}*
            📍 Location: ${it.location}
            🔗 Google Maps: $mapsUrl
            
            
        """.trimIndent()

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, shareText)
        }
        context.startActivity(Intent.createChooser(intent, "Share via"))
    }
}
