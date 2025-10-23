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

        // Registro
        composable(AppNavigation.Registro.route) {
            RegistroScreen(navController = navController)
        }

        // Home Adaptativa (con logout)
        composable(AppNavigation.Home.route) {
            HomeScreenAdaptativaWrapper(navController)
        }

        // Modo especial
        composable("modo") {
            ModoEspecialScreen()
        }

        // Perfil (cámara / galería)
        composable("perfil") {
            PerfilScreen()
        }
    }
}

// ✅ Wrapper corregido
@Composable
fun HomeScreenAdaptativaWrapper(navController: NavHostController) {
    HomeScreenAdaptativaWithLogout(
        navController = navController, // ← este parámetro faltaba
        onLogout = {
            navController.navigate(AppNavigation.Login.route) {
                popUpTo(AppNavigation.Home.route) { inclusive = true }
            }
        }
    )
}
