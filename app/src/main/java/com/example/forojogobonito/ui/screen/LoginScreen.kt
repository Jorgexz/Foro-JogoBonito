package com.example.forojogobonito.ui.screen

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.forojogobonito.R
import com.example.forojogobonito.viewmodel.UsuarioViewModel
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onNavigateToRegistro: () -> Unit,
    onNavigateToHome: () -> Unit
) {
    val context = LocalContext.current
    val activity = context as ComponentActivity
    val usuarioViewModel: UsuarioViewModel = viewModel(activity)
    val uiState by usuarioViewModel.uiState.collectAsState()

    var isLoading by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Foro Jogabonito", style = MaterialTheme.typography.headlineLarge) }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = R.drawable.pelotafutbol), // Asegúrate de tener esta imagen
                contentDescription = "Logo",
                modifier = Modifier.size(120.dp)
            )

            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                modifier = Modifier.fillMaxWidth(0.9f)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "Iniciar Sesión",
                        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.SemiBold)
                    )
                    Spacer(modifier = Modifier.height(24.dp))

                    // CAMPO CORREO
                    OutlinedTextField(
                        value = uiState.correo,
                        onValueChange = { usuarioViewModel.onCorreoChange(it) },
                        label = { Text("Correo electrónico") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // CAMPO CLAVE
                    OutlinedTextField(
                        value = uiState.clave,
                        onValueChange = { usuarioViewModel.onClaveChange(it) },
                        label = { Text("Contraseña") },
                        modifier = Modifier.fillMaxWidth(),
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Boton para hacer Login
                    Button(
                        onClick = {
                            isLoading = true
                            usuarioViewModel.login(
                                onSuccess = {
                                    isLoading = false
                                    Toast.makeText(context, "¡Bienvenido!", Toast.LENGTH_SHORT).show()
                                    onNavigateToHome()
                                },
                                onError = {
                                    isLoading = false
                                    Toast.makeText(context, "Credenciales incorrectas o Error de Conexion", Toast.LENGTH_LONG).show()
                                }
                            )
                        },
                        enabled = !isLoading,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                        } else {
                            Text("Entrar al foro")
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    TextButton(onClick = onNavigateToRegistro) {
                        Text("¿No tienes cuenta? Regístrate")
                    }
                }
            }

            // Decoración (opcional)
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}