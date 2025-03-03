package com.example.tourismchat1.screens

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.tourismchat1.data.models.TouristPlace
import com.example.tourismchat1.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItineraryScreen(navController: NavHostController) {
    val context = LocalContext.current
    val attractions = remember { mutableStateListOf<TouristPlace>() }
    var selectedPlaces by remember { mutableStateOf(listOf<Triple<TouristPlace, String, String>>()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Fetch attractions
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
        topBar = {
            TopAppBar(
                title = { Text("Itinerary Planner", color = Color.White) },
                navigationIcon = { // Back button in header
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = Color(0xFF1565C0))
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .background(Color(0xFFE3F2FD)),  // Light blue background
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Plan Your Trip",
                    fontSize = 22.sp,
                    color = Color(0xFF0D47A1),
                    modifier = Modifier.padding(16.dp)
                )

                when {
                    isLoading -> CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                    errorMessage != null -> Text(
                        errorMessage ?: "",
                        color = Color.Red,
                        modifier = Modifier.padding(16.dp)
                    )
                    else -> {
                        LazyColumn(modifier = Modifier.weight(1f)) {
                            items(attractions.chunked(2)) { rowPlaces ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp),
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    rowPlaces.forEach { place ->
                                        LocationCard(
                                            place = place,
                                            onSelectionChange = { selectedPlace, selectedDate, selectedTime ->
                                                selectedPlaces = selectedPlaces + Triple(selectedPlace, selectedDate, selectedTime)
                                            },
                                            modifier = Modifier
                                                .weight(1f) // Ensures equal width distribution
                                                .padding(4.dp)
                                        )
                                    }
                                    // If only one item in the row, add an empty spacer for balance
                                    if (rowPlaces.size == 1) {
                                        Spacer(modifier = Modifier.weight(1f))
                                    }
                                }
                            }
                        }


                        if (selectedPlaces.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(
                                onClick = { shareItinerary(context, selectedPlaces) },
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0D47A1))
                            ) {
                                Icon(Icons.Filled.Share, contentDescription = "Share", tint = Color.White)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Share Itinerary", fontSize = 18.sp, color = Color.White)
                            }
                        }
                    }
                }
            }
        }
    )
}
@Composable
fun LocationCard(
    place: TouristPlace,
    onSelectionChange: (TouristPlace, String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    var isSelected by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf("Select Date") }
    var selectedTime by remember { mutableStateOf("Select Time") }
    val context = LocalContext.current

    Card(
        modifier = modifier
            .shadow(6.dp, RoundedCornerShape(12.dp))
            .clickable { isSelected = !isSelected },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Color(0xFFBBDEFB) else Color.White
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = place.name, fontSize = 20.sp, color = Color(0xFF0D47A1))
            Text(text = "📍 ${place.location}", fontSize = 16.sp, color = Color.Gray)
            Text(text = place.description, fontSize = 14.sp, color = Color.DarkGray, modifier = Modifier.padding(top = 4.dp))

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { showDatePicker(context) { selectedDate = it } },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp)
            ) {
                Icon(Icons.Filled.CalendarToday, contentDescription = "Date", tint = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                Text(selectedDate, color = Color.White)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { showTimePicker(context) { selectedTime = it } },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp)
            ) {
                Icon(Icons.Filled.Schedule, contentDescription = "Time", tint = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                Text(selectedTime, color = Color.White)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { onSelectionChange(place, selectedDate, selectedTime) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp)
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add", tint = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Add to Itinerary", color = Color.White)
            }
        }
    }
}

// Function to show Date Picker
private fun showDatePicker(context: Context, onDateSelected: (String) -> Unit) {
    val calendar = Calendar.getInstance()
    val datePicker = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val selectedDate = "$dayOfMonth/${month + 1}/$year"
            onDateSelected(selectedDate)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )
    datePicker.show()
}

// Function to show Time Picker
private fun showTimePicker(context: Context, onTimeSelected: (String) -> Unit) {
    val calendar = Calendar.getInstance()
    val timePicker = TimePickerDialog(
        context,
        { _, hourOfDay, minute ->
            val formattedTime = String.format("%02d:%02d %s",
                if (hourOfDay % 12 == 0) 12 else hourOfDay % 12,
                minute,
                if (hourOfDay < 12) "AM" else "PM"
            )
            onTimeSelected(formattedTime)
        },
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        false
    )
    timePicker.show()
}

// Function to Share Itinerary
private fun shareItinerary(context: Context, selectedPlaces: List<Triple<TouristPlace, String, String>>) {
    if (selectedPlaces.isNotEmpty()) {
        val itineraryText = selectedPlaces.joinToString("\n") {
            "📍 ${it.first.name} on ${it.second} at ${it.third}"
        }
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, "Here’s my travel plan:\n$itineraryText")
        }
        context.startActivity(Intent.createChooser(shareIntent, "Share Itinerary via"))
    }
}
