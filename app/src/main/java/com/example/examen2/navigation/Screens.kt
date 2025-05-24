package com.example.examen2.navigation

sealed class Screen(val route: String) {
    object Planes : Screen("Planes")
    object Shipping : Screen("shipping")  // nueva pantalla

}