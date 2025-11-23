package com.example.forojogobonito.data.remote

import com.example.forojogobonito.model.Post
import com.example.forojogobonito.model.Usuario

import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("posts")
    suspend fun getPosts(): List<Post>

    @POST("posts")
    suspend fun crearPost(@Body post: Post): Post

    //Delete con ID de usuario para seguridad
    @DELETE("posts/{id}")
    suspend fun eliminarPost(
        @Path("id") id: Int,
        @Query("userId") userId: Int
    ): Response<Unit>


    //Usuarios
    @POST("usuarios/login")
    suspend fun login(@Body usuario: Usuario): Usuario

    @POST("usuarios/registro")
    suspend fun registro(@Body usuario: Usuario): Usuario
}