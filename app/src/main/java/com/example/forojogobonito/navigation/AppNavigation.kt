package com.example.forojogobonito.navigation

sealed class AppNavigation(val route: String) {
    object Login : AppNavigation("login")
    object Registro : AppNavigation("registro")
    object Home : AppNavigation("home")

}
