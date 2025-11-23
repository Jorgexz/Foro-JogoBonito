package com.example.forojogobonito.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.forojogobonito.model.Usuario
import com.example.forojogobonito.model.UsuarioErrores
import com.example.forojogobonito.model.UsuarioUiState
import com.example.forojogobonito.repository.UsuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UsuarioViewModel(
    private val repository: UsuarioRepository = UsuarioRepository()
) : ViewModel() {


    //Aqui guardamos el usuario logueado
    var usuarioActual: Usuario? = null
        private set

    private val _uiState = MutableStateFlow(UsuarioUiState())
    val uiState: StateFlow<UsuarioUiState> = _uiState

    // ... (Tus funciones onNombreChange, etc. déjalas igual) ...
    fun onNombreChange(nuevo: String) = _uiState.update { it.copy(nombre = nuevo) }
    fun onCorreoChange(nuevo: String) = _uiState.update { it.copy(correo = nuevo) }
    fun onClaveChange(nuevo: String) = _uiState.update { it.copy(clave = nuevo) }
    fun onEdadChange(nuevo: String) = _uiState.update { it.copy(edad = nuevo) }
    fun onAceptarTerminosChange(nuevo: Boolean) = _uiState.update { it.copy(aceptaTerminos = nuevo) }
    fun actualizarImagen(uri: android.net.Uri?) {
        _uiState.update { it.copy(imagenUri = uri) }
    }


    fun validarFormulario(): Boolean {
        val s = _uiState.value
        var esValido = true

        // Variables temporales para capturar los errores
        var errorNombre: String? = null
        var errorCorreo: String? = null
        var errorClave: String? = null
        var errorEdad: String? = null

        //Validar Nombre
        if (s.nombre.isBlank()) {
            errorNombre = "El nombre no puede estar vacío"
            esValido = false
        }

        //Validar Correo
        if (s.correo.isBlank()) {
            errorCorreo = "El correo es obligatorio"
            esValido = false
            //Regex (PC y Celular)
        } else if (!Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$").matches(s.correo)) {
            errorCorreo = "Ingresa un correo válido (ej: nombre@gmail.com)"
            esValido = false
        }

        //Validar Clave (Mínimo 6 caracteres)
        if (s.clave.length < 6) {
            errorClave = "La contraseña debe tener al menos 6 caracteres"
            esValido = false
        }

        //Validar Edad
        val edadNum = s.edad.toIntOrNull()
        if (edadNum == null) {
            errorEdad = "Ingresa un número"
            esValido = false
        } else if (edadNum < 10 || edadNum > 100) {
            errorEdad = "La edad debe estar entre 10 y 100 años"
            esValido = false
        }

        //Actualizamos el estado con los errores encontrados
        _uiState.update {
            it.copy(
                errores = it.errores.copy(
                    nombreError = errorNombre,
                    correoError = errorCorreo,
                    claveError = errorClave,
                    edadError = errorEdad
                )
            )
        }

        return esValido
    }

    //NUEVA FUNCION LOGIN REAL
    fun login(onSuccess: () -> Unit, onError: () -> Unit) {
        viewModelScope.launch {
            try {
                val correo = _uiState.value.correo
                val clave = _uiState.value.clave
                val usuarioRecibido = repository.login(correo, clave)

                // ¡Éxito! Guardamos al usuario en memoria
                usuarioActual = usuarioRecibido

                // Actualizamos la UI con el nombre real
                _uiState.update { it.copy(nombre = usuarioRecibido.nombre) }

                onSuccess()
            } catch (e: Exception) {
                e.printStackTrace()
                onError()
            }
        }
    }


    fun registrarUsuario(onSuccess: () -> Unit, onError: () -> Unit) {
        viewModelScope.launch {
            try {
                val s = _uiState.value
                val usuarioNuevo = repository.registro(s.nombre, s.correo, s.clave, s.edad)

                usuarioActual = usuarioNuevo
                onSuccess()
            } catch (e: Exception) {
                e.printStackTrace()
                println("ERROR AL REGISTRAR: ${e.message}") // Para ver en Logcat
                onError()
            }
        }
    }
}