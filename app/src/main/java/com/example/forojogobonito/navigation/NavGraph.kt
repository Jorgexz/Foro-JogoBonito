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

        // Home
        composable(AppNavigation.Home.route) {
            HomeScreenAdaptativaWrapper(navController)
        }

        // Partidos
        composable(AppNavigation.Partidos.route) {
            PartidosScreen(navController)
        }

        // Perfil
        composable(AppNavigation.Perfil.route) {
            PerfilResumenScreen(navController = navController)
        }

        composable(AppNavigation.PerfilResumen.route) {
            PerfilResumenScreen(navController = navController)
        }

        // Favoritos
        composable(AppNavigation.Favoritos.route) {
            FavoritosScreen(navController)
        }
    }
}

