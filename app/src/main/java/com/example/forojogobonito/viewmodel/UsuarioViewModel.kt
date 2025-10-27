package com.example.forojogobonito.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.forojogobonito.model.UsuarioErrores
import com.example.forojogobonito.model.UsuarioUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class UsuarioViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(UsuarioUiState())
    val uiState: StateFlow<UsuarioUiState> = _uiState

    fun onNombreChange(nuevo: String) =
        _uiState.update { it.copy(nombre = nuevo, errores = it.errores.copy(nombreError = null)) }

    fun onCorreoChange(nuevo: String) =
        _uiState.update { it.copy(correo = nuevo, errores = it.errores.copy(correoError = null)) }

    fun onClaveChange(nuevo: String)  =
        _uiState.update { it.copy(clave  = nuevo, errores = it.errores.copy(claveError  = null)) }

    fun onEdadChange(nuevo: String) =
        _uiState.update { it.copy(edad = nuevo.filter { c -> c.isDigit() }.take(3),
            errores = it.errores.copy(edadError = null)
        )}

    fun onAceptarTerminosChange(nuevo: Boolean) =
        _uiState.update { it.copy(aceptaTerminos = nuevo) }

    fun actualizarImagen(uri: Uri?) =
        _uiState.update { it.copy(imagenUri = uri) }


    fun validarFormulario(): Boolean {
        val actual = _uiState.value

        var nombreError: String? = null
        var correoError: String? = null
        var claveError: String? = null
        var edadError: String? = null

        if (actual.nombre.isBlank()) nombreError = "Ingresa tu nombre"

        if (actual.correo.isBlank() ||
            !android.util.Patterns.EMAIL_ADDRESS.matcher(actual.correo).matches()
        ) {
            correoError = "Correo inválido"
        }

        if (actual.clave.length < 6) {
            claveError = "La contraseña debe tener al menos 6 caracteres"
        }

        val edadInt = actual.edad.toIntOrNull()
        if (edadInt == null || edadInt !in 3..120) {
            edadError = "Ingresa una edad válida (3–120)"
        }

        _uiState.update {
            it.copy(
                errores = UsuarioErrores(
                    nombreError = nombreError,
                    correoError = correoError,
                    claveError = claveError,
                    edadError = edadError
                )
            )
        }

        val sinErrores = listOf(nombreError, correoError, claveError, edadError).all { it == null }
        return sinErrores && _uiState.value.aceptaTerminos
    }
}
