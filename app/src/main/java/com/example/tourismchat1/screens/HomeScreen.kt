package com.example.tourismapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

import com.example.tourismchat1.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    Scaffold(topBar = { TopAppBar(title = { Text("District Tourism") }) }) {
        Column(
            modifier = Modifier.padding(it).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.tourism_logo),
                contentDescription = "Tourism Logo",
                modifier = Modifier.size(150.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { navController.navigate("attractions") }, shape = RoundedCornerShape(10.dp)) {
                Text("View Attractions", fontSize = 18.sp)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { navController.navigate("map") }, shape = RoundedCornerShape(10.dp)) {
                Text("View Map", fontSize = 18.sp)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { navController.navigate("itinerary") }, shape = RoundedCornerShape(10.dp)) {
                Text("Itinerary Planner", fontSize = 18.sp)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { navController.navigate("profile") }, shape = RoundedCornerShape(10.dp)) {
                Text("Profile", fontSize = 18.sp)
            }
        }
    }
}
