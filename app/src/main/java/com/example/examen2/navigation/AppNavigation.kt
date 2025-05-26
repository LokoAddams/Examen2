package com.example.examen2.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.examen2.mapa.ShippingFormScreen
import com.example.examen2.planes.PlansCarousel
import com.example.examen2.planes.PlansViewModel


@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Planes .route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }

    ) {
        composable(Screen.Planes.route) {
            PlansCarousel(
                onPlanSelect = { navController.navigate(Screen.Shipping.route) }
            )
        }

        composable(Screen.Shipping.route) {
            ShippingFormScreen()
        }



    }


}