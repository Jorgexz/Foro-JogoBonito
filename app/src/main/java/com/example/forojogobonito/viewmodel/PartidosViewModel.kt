package com.example.forojogobonito.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.forojogobonito.data.remote.ExternalRetrofitInstance
import com.example.forojogobonito.model.Partido
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PartidosViewModel : ViewModel() {

    private val _partidos = MutableStateFlow<List<Partido>>(emptyList())
    val partidos: StateFlow<List<Partido>> = _partidos

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        cargarPartidos()
    }

    fun cargarPartidos() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // Llamamos a la API Externa
                val respuesta = ExternalRetrofitInstance.api.getProximosPartidos()
                _partidos.value = respuesta.eventos ?: emptyList()
            } catch (e: Exception) {
                e.printStackTrace()
                _partidos.value = emptyList() // Si falla, lista vacía
            } finally {
                _isLoading.value = false
            }
        }
    }
}