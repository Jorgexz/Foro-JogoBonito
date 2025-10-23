package com.example.forojogobonito.ui.components

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun ImagenInteligente(uri: Uri?, modifier: Modifier = Modifier) {
    if (uri != null) {
        // Muestra la imagen seleccionada en forma circular
        AsyncImage(
            model = uri,
            contentDescription = "Foto de perfil",
            modifier = modifier
                .size(140.dp)
                .clip(CircleShape)
        )
    } else {
        // Si no hay imagen, muestra un ícono por defecto
        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = "Sin imagen",
            tint = Color.Gray,
            modifier = modifier.size(140.dp)
        )
    }
}
