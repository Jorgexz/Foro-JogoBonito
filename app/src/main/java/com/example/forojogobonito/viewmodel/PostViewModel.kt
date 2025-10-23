package com.example.forojogobonito.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.forojogobonito.model.Post
import com.example.forojogobonito.repository.PostRepository

class PostViewModel : ViewModel() {

    // Lista observable de posts para Compose
    private val _posts = mutableStateListOf<Post>().apply {
        addAll(PostRepository.obtenerPosts())
    }
    val posts: List<Post> get() = _posts

    // Función para agregar un post nuevo
    fun agregarPost(titulo: String, contenido: String, autorId: String) {
        val nuevoPost = Post(
            id = (_posts.size + 1).toString(), // id simple incremental
            titulo = titulo,
            contenido = contenido,
            autorId = autorId,
            fechaPublicacion = "2025-10-13" // puedes usar la fecha actual más adelante
        )
        PostRepository.agregarPost(nuevoPost)
        _posts.add(nuevoPost)
    }
}