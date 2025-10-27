package com.example.forojogobonito.ui.screen

import android.content.Intent
import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.forojogobonito.R
import com.example.forojogobonito.navigation.AppNavigation
import com.example.forojogobonito.viewmodel.UsuarioViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroScreen(
    navController: NavController
) {
    val context = LocalContext.current
    val activity = context as ComponentActivity
    val usuarioViewModel: UsuarioViewModel = viewModel(activity)
    val uiState by usuarioViewModel.uiState.collectAsState()

    val galeriaLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        if (uri != null) {
            try {
                context.contentResolver.takePersistableUriPermission(
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            } catch (_: SecurityException) { }
            usuarioViewModel.actualizarImagen(uri)
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Registro de Usuario", style = MaterialTheme.typography.titleLarge) }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (uiState.imagenUri != null) {
                Image(
                    painter = rememberAsyncImagePainter(uiState.imagenUri),
                    contentDescription = "Foto de perfil",
                    modifier = Modifier.size(96.dp).clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else {
                Image(
                    painter = androidx.compose.ui.res.painterResource(id = R.drawable.pelotafutbol),
                    contentDescription = "Foto por defecto",
                    modifier = Modifier.size(96.dp).clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(Modifier.height(8.dp))
            OutlinedButton(onClick = { galeriaLauncher.launch(arrayOf("image/*")) }) {
                Text("Seleccionar foto")
            }

            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = uiState.nombre,
                onValueChange = { usuarioViewModel.onNombreChange(it) },
                label = { Text("Nombre") },
                isError = uiState.errores.nombreError != null,
                supportingText = { uiState.errores.nombreError?.let { Text(it) } },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = uiState.correo,
                onValueChange = { usuarioViewModel.onCorreoChange(it) },
                label = { Text("Correo") },
                isError = uiState.errores.correoError != null,
                supportingText = { uiState.errores.correoError?.let { Text(it) } },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = uiState.clave,
                onValueChange = { usuarioViewModel.onClaveChange(it) },
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                isError = uiState.errores.claveError != null,
                supportingText = { uiState.errores.claveError?.let { Text(it) } },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = uiState.edad,
                onValueChange = { usuarioViewModel.onEdadChange(it) },
                label = { Text("Edad") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = uiState.errores.edadError != null,
                supportingText = { uiState.errores.edadError?.let { Text(it) } },
                modifier = Modifier.fillMaxWidth()
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
                        navController.navigate(AppNavigation.PerfilResumen.route)
                    }
                },
                enabled = uiState.aceptaTerminos,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Registrar")
            }
        }
    }
}
