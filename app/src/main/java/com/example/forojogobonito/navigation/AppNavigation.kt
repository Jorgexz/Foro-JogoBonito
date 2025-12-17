package com.example.forojogobonito.navigation

sealed class AppNavigation(val route: String) {
    object Login : AppNavigation("login")
    object Registro : AppNavigation("registro")
    object Home : AppNavigation("home")
    object Perfil : AppNavigation("perfil")
    object PerfilResumen : AppNavigation("perfil_resumen")
    object Partidos : AppNavigation("partidos_screen")

    object Favoritos : AppNavigation("favoritos")
}
