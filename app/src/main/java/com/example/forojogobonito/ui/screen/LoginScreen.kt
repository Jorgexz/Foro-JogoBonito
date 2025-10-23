package com.example.forojogobonito.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(onNavigateToRegistro: () -> Unit, onNavigateToHome: () -> Unit) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Iniciar Sesión") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Pantalla de Login")
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onNavigateToHome) { Text("Entrar al foro") }
            Spacer(modifier = Modifier.height(8.dp))
            TextButton(onClick = onNavigateToRegistro) { Text("¿No tienes cuenta? Regístrate") }
        }
    }
}
