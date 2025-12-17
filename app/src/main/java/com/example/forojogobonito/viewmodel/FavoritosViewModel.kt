package com.example.forojogobonito.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.forojogobonito.data.local.AppDatabase
import com.example.forojogobonito.data.local.PostFavorito
import com.example.forojogobonito.model.Post
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class FavoritosViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getDatabase(application)
    private val dao = db.favoritosDao()

    private val _favoritos = MutableStateFlow<List<PostFavorito>>(emptyList())
    val favoritos: StateFlow<List<PostFavorito>> = _favoritos

    init {
        cargarFavoritos()
    }

    private fun cargarFavoritos() {
        viewModelScope.launch {
            dao.obtenerFavoritos().collect { lista ->
                _favoritos.value = lista
            }
        }
    }

    fun guardarEnFavoritos(post: Post) {
        viewModelScope.launch {
            val favorito = PostFavorito(
                id = post.id ?: 0,
                titulo = post.titulo,
                contenido = post.contenido,
                autor = post.autor
            )
            dao.guardarFavorito(favorito)
        }
    }

    fun borrarDeFavoritos(favorito: PostFavorito) {
        viewModelScope.launch {
            dao.eliminarFavorito(favorito)
        }
    }
}