package com.example.forojogobonito.ui.screen

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.forojogobonito.navigation.AppNavigation
import com.example.forojogobonito.utils.obtenerWindowSizeClass

@Composable
fun HomeScreenAdaptativaWrapper(navController: NavHostController) {
    // Definimos la acción de Logout aquí
    val onLogoutAction = {
        navController.navigate(AppNavigation.Login.route) {
            popUpTo(AppNavigation.Home.route) { inclusive = true }
        }
    }

    // Obtenemos el tamaño de pantalla
    val windowSize = obtenerWindowSizeClass()

    // Decidimos qué pantalla mostrar
    when (windowSize.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            HomeScreenCompact(navController, onLogout = onLogoutAction)
        }
        else -> {
            // Por defecto usamos la compacta si no hay otra definida
            HomeScreenCompact(navController, onLogout = onLogoutAction)
        }
    }
}