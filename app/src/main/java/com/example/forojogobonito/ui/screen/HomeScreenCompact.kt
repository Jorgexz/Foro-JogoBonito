package com.example.forojogobonito.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.forojogobonito.viewmodel.UsuarioViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenCompact(
    navController: NavController,
    onLogout: () -> Unit = {}
) {
    var selectedTab by remember { mutableStateOf(0) } // 0 = inicio, 1 = cuenta

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Foro Futbolero (Compact)") })
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    label = { Text("Inicio") },
                    icon = {}
                )
                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    label = { Text("Cuenta") },
                    icon = {}
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onLogout,
                    label = { Text("Salir") },
                    icon = {}
                )
            }
        }
    ) { padding ->
        when (selectedTab) {
            0 -> PantallaInicio(padding)
            1 -> PantallaCuenta(padding, navController)
        }
    }
}

@Composable
fun PantallaInicio(padding: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Versión compacta (Inicio)")
        Spacer(modifier = Modifier.height(10.dp))
        Text("Aquí iría el feed de publicaciones del foro.")
    }
}

@Composable
fun PantallaCuenta(
    padding: PaddingValues,
    navController: NavController,
    usuarioViewModel: UsuarioViewModel = viewModel()
) {
    val uiState by usuarioViewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text("👤 Nombre: ${uiState.nombre}")
        Text("📧 Correo: ${uiState.correo}")
        Text("🏠 Dirección: ${uiState.direccion}")
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            "Más adelante podrás editar tus datos aquí.",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = { navController.navigate("modo") },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Ir al modo especial")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreenCompact() {
    // Se omite el NavController en el preview
    // Solo para vista previa
}
