package com.example.tourismchat1.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tourismchat1.screens.*
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.tourismchat1.screens.AttractionDetailsScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("attractions") { AttractionsScreen(navController) }
        composable("map") { MapScreen(navController) }
        composable("itinerary") { ItineraryScreen(navController) }
        composable("about") { About(navController) }
        composable(
            route = "attraction_details/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")
            AttractionDetailsScreen(navController, id)
        }
    }
}
