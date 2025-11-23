package com.example.forojogobonito.repository

import com.example.forojogobonito.data.remote.RetrofitInstance
import com.example.forojogobonito.model.Post

class PostRepository {

    suspend fun obtenerPosts(): List<Post> {
        return RetrofitInstance.api.getPosts()
    }

    suspend fun crearPost(post: Post): Post {
        //el post ya viene con el usuario_id dentro desde el ViewModel
        return RetrofitInstance.api.crearPost(post)
    }

    //recibe 2 ids, del post y el del usuario que quiere borrar
    suspend fun eliminarPost(idPost: Int, idUsuario: Int) {
        val response = RetrofitInstance.api.eliminarPost(idPost, idUsuario)
        if (!response.isSuccessful) {
            throw Exception("Error al borrar: ${response.code()} (Posiblemente no eres el dueño)")
        }
    }

    suspend fun editarPost(post: Post) {
        //el post ya debe traer el ID y el usuario_id actualizados
        post.id?.let { id ->
            val response = RetrofitInstance.api.editarPost(id, post)
            if (!response.isSuccessful) throw Exception("Error al editar")
        }
    }

}