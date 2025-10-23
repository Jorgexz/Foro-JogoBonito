package com.example.forojogobonito.ui.screen


import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import com.example.forojogobonito.utils.obtenerWindowSizeClass

@Composable
fun HomeScreenAdaptativaWithLogout(onLogout: () -> Unit) {
    val windowSizeClass = obtenerWindowSizeClass()

    when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact -> HomeScreenCompact(onLogout)
        WindowWidthSizeClass.Medium -> HomeScreenMedium()
        WindowWidthSizeClass.Expanded -> HomeScreenExpanded()
        else -> HomeScreenCompact(onLogout)
    }
}

