package com.example.forojogobonito.repository

import com.example.forojogobonito.data.Post
import com.example.forojogobonito.data.PostDao
import kotlinx.coroutines.flow.Flow

class PostRepository(private val postDao: PostDao) {

    val posts: Flow<List<Post>> = postDao.obtenerPosts()

    suspend fun insertar(post: Post) = postDao.insertarPost(post)

    suspend fun actualizar(post: Post) = postDao.actualizarPost(post)

    suspend fun eliminar(post: Post) = postDao.eliminarPost(post)
}
