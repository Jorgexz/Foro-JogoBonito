package com.example.forojogobonito.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.forojogobonito.navigation.AppNavigation
import com.example.forojogobonito.viewmodel.UsuarioViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroScreen(
    navController: NavController,
    usuarioViewModel: UsuarioViewModel = viewModel()
) {
    val uiState by usuarioViewModel.uiState.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Registro de Usuario") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = uiState.nombre,
                onValueChange = { usuarioViewModel.onNombreChange(it) },
                label = { Text("Nombre") },
                isError = uiState.errores.nombreError != null,
                supportingText = { uiState.errores.nombreError?.let { Text(it) } }
            )

            OutlinedTextField(
                value = uiState.correo,
                onValueChange = { usuarioViewModel.onCorreoChange(it) },
                label = { Text("Correo") },
                isError = uiState.errores.correoError != null,
                supportingText = { uiState.errores.correoError?.let { Text(it) } }
            )

            OutlinedTextField(
                value = uiState.clave,
                onValueChange = { usuarioViewModel.onClaveChange(it) },
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                isError = uiState.errores.claveError != null,
                supportingText = { uiState.errores.claveError?.let { Text(it) } }
            )

            OutlinedTextField(
                value = uiState.direccion,
                onValueChange = { usuarioViewModel.onDireccionChange(it) },
                label = { Text("Dirección") },
                isError = uiState.errores.direccionError != null,
                supportingText = { uiState.errores.direccionError?.let { Text(it) } }
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = uiState.aceptaTerminos,
                    onCheckedChange = { usuarioViewModel.onAceptarTerminosChange(it) }
                )
                Text("Acepto los términos y condiciones")
            }

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    if (usuarioViewModel.validarFormulario()) {
                        navController.navigate(AppNavigation.Resumen.route)
                    }
                },
                enabled = uiState.aceptaTerminos
            ) {
                Text("Registrar")
            }
        }
    }
}
