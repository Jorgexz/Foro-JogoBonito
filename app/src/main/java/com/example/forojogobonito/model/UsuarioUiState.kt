package com.example.forojogobonito.model

import android.net.Uri

data class UsuarioUiState(
    val nombre: String = "",
    val correo: String = "",
    val clave: String = "",
    val edad: String = "",
    val aceptaTerminos: Boolean = false,
    val imagenUri: Uri? = null,
    val errores: UsuarioErrores = UsuarioErrores()
)
