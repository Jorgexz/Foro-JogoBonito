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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.forojogobonito.R
import com.example.forojogobonito.navigation.AppNavigation
import com.example.forojogobonito.viewmodel.UsuarioViewModel
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilResumenScreen(
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
                title = { Text("Mi Perfil", style = MaterialTheme.typography.headlineMedium) }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (uiState.imagenUri != null) {
                Image(
                    painter = rememberAsyncImagePainter(uiState.imagenUri),
                    contentDescription = "Foto de perfil",
                    modifier = Modifier.size(140.dp).clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.ic_perfil),
                    contentDescription = "Foto por defecto",
                    modifier = Modifier.size(140.dp).clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(Modifier.height(16.dp))
            OutlinedButton(
                onClick = { galeriaLauncher.launch(arrayOf("image/*")) },
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color.White
                )
            ) {
                Text("Cambiar foto")
            }

            Spacer(Modifier.height(28.dp))

            // Datos con separadores
            PerfilDatoLinea("Nombre: ${uiState.nombre}")
            PerfilDatoLinea("Correo: ${uiState.correo}")
            PerfilDatoLinea("Edad: ${uiState.edad.ifBlank { "—" }}")

            Spacer(Modifier.height(24.dp))
            Button(
                onClick = {
                    navController.navigate(AppNavigation.Home.route) {
                        popUpTo(AppNavigation.Home.route) { inclusive = true }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Volver al foro")
            }
        }
    }
}

@Composable
private fun PerfilDatoLinea(texto: String) {
    Column(Modifier.fillMaxWidth()) {
        Text(texto, style = MaterialTheme.typography.bodyLarge)
        Divider(Modifier.padding(vertical = 10.dp))
    }
}
