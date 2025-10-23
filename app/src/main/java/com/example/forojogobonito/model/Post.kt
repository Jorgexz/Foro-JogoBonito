package com.example.forojogobonito.model


data class Post(
    val id: String = "",
    val titulo: String = "",
    val contenido: String = "",
    val autorId: String = "",
    val fechaPublicacion: String = "",
    val comentarios: List<Comentario> = emptyList()
)
