package com.example.tourismchat1.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(navController: NavHostController) {
    val bhojpurLocation = LatLng(25.4691, 84.4633) // Center of Bhojpur
    val markerLocations = listOf(
        LatLng(25.5525, 84.6628) to "Veer Kunwar Singh Fort",
        LatLng(25.4691, 84.4633) to "Aranya Devi Temple",
        LatLng(25.4792, 84.5543) to "Jagdishpur Fort"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Explore Bhojpur") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            GoogleMapView(
                initialPosition = bhojpurLocation,
                markerLocations = markerLocations
            )
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun GoogleMapView(initialPosition: LatLng, markerLocations: List<Pair<LatLng, String>>) {
    val cameraPositionState = rememberCameraPositionState {
        position = com.google.android.gms.maps.model.CameraPosition.fromLatLngZoom(initialPosition, 10f)
    }

    GoogleMap(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp),
        cameraPositionState = cameraPositionState
    ) {
        markerLocations.forEach { (location, title) ->
            Marker(
                state = rememberMarkerState(position = location),
                title = title
            )
        }
    }
}
