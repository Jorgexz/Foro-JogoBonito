package com.example.forojogobonito.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.forojogobonito.ui.screen.*

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = AppNavigation.Login.route
    ) {
        // Login
        composable(AppNavigation.Login.route) {
            LoginScreen(
                onNavigateToRegistro = { navController.navigate(AppNavigation.Registro.route) },
                onNavigateToHome = {
                    navController.navigate(AppNavigation.Home.route) {
                        popUpTo(AppNavigation.Login.route) { inclusive = true }
                    }
                }
            )
        }

        // Registro  (pásale el navController)
        composable(AppNavigation.Registro.route) {
            RegistroScreen(navController = navController)
        }

        // Home Adaptativa (con logout)
        composable(AppNavigation.Home.route) {
            HomeScreenAdaptativaWrapper(navController)
        }




    }
}

// Wrapper para inyectar el onLogout hacia la Home adaptativa
@Composable
fun HomeScreenAdaptativaWrapper(navController: NavHostController) {
    HomeScreenAdaptativaWithLogout(
        onLogout = {
            navController.navigate(AppNavigation.Login.route) {
                popUpTo(AppNavigation.Home.route) { inclusive = true }
            }
        }
    )
}
