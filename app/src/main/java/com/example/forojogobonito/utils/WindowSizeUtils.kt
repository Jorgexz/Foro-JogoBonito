package com.example.forojogobonito.utils

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass

// ✅ Define LocalActivity
val LocalActivity = staticCompositionLocalOf<Activity> {
    error("No Activity found!")
}

@Composable
fun ProvideActivity(content: @Composable () -> Unit) {
    val context = LocalContext.current
    val activity = context as? Activity
        ?: error("No se pudo obtener Activity desde el contexto.")

    CompositionLocalProvider(LocalActivity provides activity, content = content)
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun obtenerWindowSizeClass(): WindowSizeClass {
    return calculateWindowSizeClass(LocalActivity.current)
}
