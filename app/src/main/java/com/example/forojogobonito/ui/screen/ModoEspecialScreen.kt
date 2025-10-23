package com.example.forojogobonito.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// ---------------------------
// VIEWMODEL
// ---------------------------
class ModoEspecialViewModel : ViewModel() {

    private val _modoActivo = MutableStateFlow(false)
    val modoActivo: StateFlow<Boolean> = _modoActivo

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _mostrarMensaje = MutableStateFlow(false)
    val mostrarMensaje: StateFlow<Boolean> = _mostrarMensaje

    init {
        // Simular carga inicial
        viewModelScope.launch {
            delay(1500)
            _isLoading.value = false
        }
    }

    fun alternarModo() {
        _modoActivo.value = !_modoActivo.value
        guardarEnDataStore(_modoActivo.value)
        mostrarMensajeTemporal()
    }

    private fun guardarEnDataStore(valor: Boolean) {
        // más adelante se implementará con DataStore Preferences
        println("Estado guardado: $valor")
    }

    private fun mostrarMensajeTemporal() {
        viewModelScope.launch {
            _mostrarMensaje.value = true
            delay(1200)
            _mostrarMensaje.value = false
        }
    }
}

// ---------------------------
// UI
// ---------------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModoEspecialScreen(viewModel: ModoEspecialViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val modoActivo by viewModel.modoActivo.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val mostrarMensaje by viewModel.mostrarMensaje.collectAsState()

    val colorFondo by animateColorAsState(
        targetValue = if (modoActivo) Color(0xFF1B5E20) else Color(0xFFB71C1C),
        label = "colorFondo"
    )

    val textoModo by remember(modoActivo) {
        derivedStateOf { if (modoActivo) "Modo Especial ACTIVADO" else "Modo Especial DESACTIVADO" }
    }

    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        Scaffold(
            topBar = {
                TopAppBar(title = { Text("Animaciones y Estados") })
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(colorFondo)
                    .padding(20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = textoModo,
                    color = Color.White,
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                )
                Spacer(Modifier.height(20.dp))
                Button(
                    onClick = { viewModel.alternarModo() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                ) {
                    Text(
                        if (modoActivo) "Desactivar" else "Activar",
                        color = if (modoActivo) Color(0xFF1B5E20) else Color(0xFFB71C1C)
                    )
                }

                AnimatedVisibility(visible = mostrarMensaje) {
                    Text(
                        "✅ Estado guardado correctamente",
                        color = Color.White,
                        modifier = Modifier.padding(top = 20.dp)
                    )
                }
            }
        }
    }
}
