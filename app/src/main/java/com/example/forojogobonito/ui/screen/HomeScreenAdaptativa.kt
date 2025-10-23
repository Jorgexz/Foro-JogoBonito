package com.example.forojogobonito.ui.screen

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.forojogobonito.utils.obtenerWindowSizeClass

@Composable
fun HomeScreenAdaptativaWithLogout(
    navController: NavController,
    onLogout: () -> Unit
) {
    val windowSizeClass = obtenerWindowSizeClass()

    when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact -> HomeScreenCompact(navController, onLogout)
        WindowWidthSizeClass.Medium -> HomeScreenMedium()
        WindowWidthSizeClass.Expanded -> HomeScreenExpanded()
        else -> HomeScreenCompact(navController, onLogout)
    }
}
