package com.example.forojogobonito.ui.screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.forojogobonito.ui.components.ImagenInteligente
import com.example.forojogobonito.viewmodel.PerfilViewModel
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilScreen(viewModel: PerfilViewModel = viewModel()) {
    val imagenUri by viewModel.imagenUri.collectAsState()

    val contexto = LocalContext.current
    val uriTemporal = remember { mutableStateOf<Uri?>(null) }

    // Launcher para abrir la galería
    val galeriaLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        viewModel.actualizarDesdeGaleria(uri)
    }

    // Launcher para abrir la cámara
    val camaraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { exito ->
        if (exito) {
            viewModel.actualizarDesdeCamara(uriTemporal.value)
        }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Perfil de Usuario") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ImagenInteligente(uri = imagenUri)
            Spacer(Modifier.height(20.dp))

            Button(onClick = { galeriaLauncher.launch("image/*") }) {
                Text("Seleccionar desde galería")
            }

            Spacer(Modifier.height(10.dp))

            Button(onClick = {
                val archivo = File.createTempFile("foto", ".jpg", contexto.cacheDir)
                val uri = FileProvider.getUriForFile(
                    contexto,
                    "${contexto.packageName}.provider",
                    archivo
                )
                uriTemporal.value = uri
                camaraLauncher.launch(uri)
            }) {
                Text("Tomar foto con cámara")
            }
        }
    }
}
