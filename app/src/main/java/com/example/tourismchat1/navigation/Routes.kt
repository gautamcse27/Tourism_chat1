package com.example.tourismchat1.navigation

sealed class Routes(val route: String) {
    object Home : Routes("home")
    object Attractions : Routes("attractions")
    object About : Routes("about")
    object Contact : Routes("contact")
}
