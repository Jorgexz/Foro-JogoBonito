package com.example.forojogobonito.model

data class Usuario(
    val id: Int = 0,
    val nombre: String,
    val correo: String,
    val clave: String,
    val edad: Int? = null,        // Coincide con tu DB
    val imagen_url: String? = null,
    val rol: String = "user"
)