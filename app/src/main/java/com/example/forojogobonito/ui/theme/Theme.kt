package com.example.forojogobonito.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = PitchGreenDark,
    onPrimary = Color.White,
    secondary = DeepNavyDark,
    tertiary = AcentoAmarilloDark,
    surface = SurfaceDark,
    onSurface = Color(0xFFECECEC),
    outline = OutlineDark,
    outlineVariant = OutlineDark
)

private val LightColorScheme = lightColorScheme(
    primary = PitchGreen,
    onPrimary = Color.White,
    secondary = DeepNavy,
    tertiary = AcentoAmarillo,
    surface = SurfaceLight,
    onSurface = Color(0xFF1C1B1F),
    outline = OutlineLight,
    outlineVariant = OutlineLight
)

@Composable
fun ForoJogoBonitoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val colorScheme =
        if (dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        } else {
            if (darkTheme) DarkColorScheme else LightColorScheme
        }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
