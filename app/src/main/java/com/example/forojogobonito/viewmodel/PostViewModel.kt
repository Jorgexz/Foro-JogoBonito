package com.example.forojogobonito.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.forojogobonito.data.AppDatabase
import com.example.forojogobonito.data.Post
import com.example.forojogobonito.repository.PostRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

import java.time.LocalDate

class PostViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: PostRepository

    val posts: StateFlow<List<Post>>

    init {
        val dao = AppDatabase.getDatabase(application).postDao()
        repository = PostRepository(dao)
        posts = repository.posts.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    }

    fun agregarPost(titulo: String, contenido: String, autor: String, categoria: String) {
        viewModelScope.launch {
            val fecha = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            val nuevoPost = Post(
                titulo = titulo,
                contenido = contenido,
                autor = autor,
                categoria = categoria,
                fecha = fecha
            )
            repository.insertar(nuevoPost)
        }
    }


    fun eliminarPost(post: Post) {
        viewModelScope.launch {
            repository.eliminar(post)
        }
    }

    fun actualizarPost(post: Post) {
        viewModelScope.launch {
            repository.actualizar(post)
        }
    }
}
