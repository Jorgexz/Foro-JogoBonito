package com.example.forojogobonito.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.forojogobonito.model.Post
import com.example.forojogobonito.repository.PostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PostViewModel : ViewModel() {

    private val repository = PostRepository()

    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    val posts: StateFlow<List<Post>> = _posts

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    init {
        cargarPosts()
    }

    fun cargarPosts() {
        viewModelScope.launch {
            try {
                _posts.value = repository.obtenerPosts()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    fun agregarPost(titulo: String, contenido: String, autor: String, categoria: String, usuarioId: Int) {
        viewModelScope.launch {
            val fecha = dateFormat.format(Date())
            val nuevoPost = Post(
                titulo = titulo,
                contenido = contenido,
                autor = autor,
                categoria = categoria,
                fecha = fecha,
                usuario_id = usuarioId // 👈 IMPORTANTE
            )
            try {
                repository.crearPost(nuevoPost)
                cargarPosts()
            } catch (e: Exception) { e.printStackTrace() }
        }
    }

    fun eliminarPost(post: Post, usuarioIdActual: Int) {
        viewModelScope.launch {
            try {
                post.id?.let { idPost ->
                    // Enviamos mi ID para demostrar que soy el dueño
                    repository.eliminarPost(idPost, usuarioIdActual)
                    cargarPosts()
                }
            } catch (e: Exception) {
                println("No se pudo borrar: ${e.message}")
            }
        }
    }
}

