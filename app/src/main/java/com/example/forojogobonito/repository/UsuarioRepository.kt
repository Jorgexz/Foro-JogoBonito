package com.example.forojogobonito.repository

import com.example.forojogobonito.data.remote.RetrofitInstance
import com.example.forojogobonito.model.Usuario

class UsuarioRepository {

    suspend fun login(correo: String, clave: String): Usuario {
        // Creamos un objeto usuario temporal para enviar credenciales
        val request = Usuario(nombre = "", correo = correo, clave = clave)
        return RetrofitInstance.api.login(request)
    }

    suspend fun registro(nombre: String, correo: String, clave: String, edad: String): Usuario {
        // Convertimos edad a Int (o null si está vacío)
        val edadInt = edad.toIntOrNull()

        val request = Usuario(
            nombre = nombre,
            correo = correo,
            clave = clave,
            edad = edadInt
        )
        return RetrofitInstance.api.registro(request)
    }
}