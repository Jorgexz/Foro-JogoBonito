package com.example.forojogobonito.model

data class Post(
    val id: Int? = null,
    val titulo: String,
    val contenido: String,
    val autor: String,
    val categoria: String,
    val fecha: String,
    val usuario_id: Int? = null
)