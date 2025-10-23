package com.example.forojogobonito.viewmodel

import androidx.lifecycle.ViewModel
import com.example.forojogobonito.model.UsuarioUiState
import com.example.forojogobonito.model.UsuarioErrores
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class UsuarioViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(UsuarioUiState())
    val uiState: StateFlow<UsuarioUiState> = _uiState

    // Actualización de campos
    fun onNombreChange(nuevo: String) = _uiState.update { it.copy(nombre = nuevo) }
    fun onCorreoChange(nuevo: String) = _uiState.update { it.copy(correo = nuevo) }
    fun onClaveChange(nuevo: String) = _uiState.update { it.copy(clave = nuevo) }
    fun onDireccionChange(nuevo: String) = _uiState.update { it.copy(direccion = nuevo) }
    fun onAceptarTerminosChange(nuevo: Boolean) = _uiState.update { it.copy(aceptaTerminos = nuevo) }

    // Validaciones simples
    fun validarFormulario(): Boolean {
        var valido = true
        var errores = UsuarioErrores()

        if (_uiState.value.nombre.isBlank()) {
            errores = errores.copy(nombreError = "El nombre no puede estar vacío")
            valido = false
        }

        if (!_uiState.value.correo.contains("@")) {
            errores = errores.copy(correoError = "Correo inválido")
            valido = false
        }

        if (_uiState.value.clave.length < 6) {
            errores = errores.copy(claveError = "La clave debe tener al menos 6 caracteres")
            valido = false
        }

        if (_uiState.value.direccion.isBlank()) {
            errores = errores.copy(direccionError = "La dirección es obligatoria")
            valido = false
        }

        _uiState.update { it.copy(errores = errores) }
        return valido
    }
}
