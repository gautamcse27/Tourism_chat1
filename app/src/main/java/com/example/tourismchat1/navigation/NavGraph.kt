package com.example.tourismchat1.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tourismapp.ui.screens.*

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("attractions") { AttractionsScreen(navController) }
        composable("map") { MapScreen(navController) }
        composable("itinerary") { ItineraryScreen(navController) }
        composable("profile") { ProfileScreen(navController) }
    }
}
