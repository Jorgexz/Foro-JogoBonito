package com.example.forojogobonito.data.remote

data class PostRequest(
    val titulo: String,
    val contenido: String,
    val autor: String,
    val categoria: String,
    val fecha: String
)
