package com.example.forojogobonito.repository

import com.example.forojogobonito.model.Post

// Singleton que simula la base de datos de posts
object PostRepository {
    private val posts = mutableListOf<Post>()

    // Retorna todos los posts
    fun obtenerPosts(): List<Post> = posts

    // Agrega un nuevo post
    fun agregarPost(post: Post) {
        posts.add(post)
    }
}
