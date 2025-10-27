package com.example.forojogobonito.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PerfilViewModel : ViewModel() {

    //Guarda el URI de la imagen actual (de la cámara o galería)
    private val _imagenUri = MutableStateFlow<Uri?>(null)
    val imagenUri: StateFlow<Uri?> = _imagenUri

    //Actualiza cuando se elige una imagen desde la galería
    fun actualizarDesdeGaleria(uri: Uri?) {
        _imagenUri.value = uri
    }

    //Actualiza cuando se toma una foto desde la cámara
    fun actualizarDesdeCamara(uri: Uri?) {
        _imagenUri.value = uri
    }
}
