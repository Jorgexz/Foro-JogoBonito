package com.example.forojogobonito.ui.screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.forojogobonito.ui.components.ImagenInteligente
import com.example.forojogobonito.viewmodel.PerfilViewModel
import com.example.forojogobonito.navigation.AppNavigation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilScreen(
    navController: NavController,
    viewModel: PerfilViewModel = viewModel()
) {
    val imagenUri by viewModel.imagenUri.collectAsState()


    val galeriaLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        viewModel.actualizarDesdeGaleria(uri)
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

            // 🔹 Botón para abrir galería
            Button(onClick = { galeriaLauncher.launch("image/*") }) {
                Text("Seleccionar desde galería")
            }

            Spacer(Modifier.height(20.dp))


            Button(
                onClick = {

                    navController.navigate(AppNavigation.Home.route) {
                        popUpTo(AppNavigation.Registro.route) { inclusive = true }
                    }
                },
                enabled = imagenUri != null,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar perfil")
            }
        }
    }
}
