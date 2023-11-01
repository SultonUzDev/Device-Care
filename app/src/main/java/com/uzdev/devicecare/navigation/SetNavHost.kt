package com.uzdev.devicecare.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.uzdev.devicecare.presentation.MainScreen
import com.uzdev.devicecare.presentation.apps.AppsScreen
import com.uzdev.devicecare.presentation.apps.details.AppDetailsScreen
import com.uzdev.devicecare.presentation.onboard.OnBoardingScreen
import com.uzdev.devicecare.presentation.storage.StorageScreen

@Composable
fun SetNavHost(navHostController: NavHostController, startDestination: String) {

    NavHost(navController = navHostController, startDestination = startDestination) {
        composable(NavRoutes.OnBoarding.route) {
            OnBoardingScreen(navController = navHostController)
        }


        composable(NavRoutes.Main.route) {
            MainScreen(navHostController)
        }

        composable(NavRoutes.Apps.route) {
            AppsScreen(navHostController)
        }

        composable(NavRoutes.Storage.route) {
            StorageScreen(navHostController = navHostController)
        }
        composable(
            route = NavRoutes.AppDetails.route + "/{packageName}",
            arguments = listOf(navArgument(name = "packageName") {
                type = NavType.StringType
            })

        ) { navBackStackEntry ->
            navBackStackEntry.arguments?.let { bundle ->
                val pn = bundle.getString("packageName")
                pn?.let {
                    AppDetailsScreen(navHostController, packageName = it)
                }


            }
        }
    }
}