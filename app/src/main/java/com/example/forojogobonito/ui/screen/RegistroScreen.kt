package com.example.forojogobonito.ui.screen

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.forojogobonito.navigation.AppNavigation
import com.example.forojogobonito.viewmodel.UsuarioViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroScreen(navController: NavController) {
    val context = LocalContext.current
    val activity = context as ComponentActivity
    val usuarioViewModel: UsuarioViewModel = viewModel(activity)
    val uiState by usuarioViewModel.uiState.collectAsState()

    var isLoading by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text("Crear Cuenta") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // NOMBRE
            OutlinedTextField(
                value = uiState.nombre,
                onValueChange = { usuarioViewModel.onNombreChange(it) },
                label = { Text("Nombre de Usuario") },
                modifier = Modifier.fillMaxWidth(),
                isError = uiState.errores.nombreError != null,
                supportingText = { uiState.errores.nombreError?.let { Text(it) } }
            )

            // CORREO
            OutlinedTextField(
                value = uiState.correo,
                onValueChange = { usuarioViewModel.onCorreoChange(it) },
                label = { Text("Correo electrónico") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                isError = uiState.errores.correoError != null,
                supportingText = { uiState.errores.correoError?.let { Text(it) } }
            )

            // CLAVE
            OutlinedTextField(
                value = uiState.clave,
                onValueChange = { usuarioViewModel.onClaveChange(it) },
                label = { Text("Contraseña") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                isError = uiState.errores.claveError != null,
                supportingText = { uiState.errores.claveError?.let { Text(it) } }
            )

            // EDAD
            OutlinedTextField(
                value = uiState.edad,
                onValueChange = { usuarioViewModel.onEdadChange(it) },
                label = { Text("Edad") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = uiState.errores.edadError != null,
                supportingText = { uiState.errores.edadError?.let { Text(it) } }
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = uiState.aceptaTerminos,
                    onCheckedChange = { usuarioViewModel.onAceptarTerminosChange(it) }
                )
                Text("Acepto los términos y condiciones")
            }

            Spacer(modifier = Modifier.height(16.dp))

            //BOTON REGISTRO
            Button(
                onClick = {
                    if (usuarioViewModel.validarFormulario()) {
                        isLoading = true
                        usuarioViewModel.registrarUsuario(
                            onSuccess = {
                                isLoading = false
                                Toast.makeText(context, "¡Cuenta creada con exito!", Toast.LENGTH_SHORT).show()
                                navController.navigate(AppNavigation.Home.route) {
                                    popUpTo(AppNavigation.Login.route) { inclusive = true }
                                }
                            },
                            onError = {
                                isLoading = false
                                Toast.makeText(context, "Error al registrar. Revisa tu conexión o correo duplicado.", Toast.LENGTH_LONG).show()
                            }
                        )
                    }
                },
                enabled = uiState.aceptaTerminos && !isLoading,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp))
                } else {
                    Text("Registrar")
                }
            }
        }
    }
}