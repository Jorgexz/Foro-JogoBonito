package com.example.forojogobonito.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenExpanded() {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Foro Futbolero (Expanded)") })
        }
    ) { padding ->
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text("Lista de publicaciones", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Aquí podrías mostrar los posts en un grid o con más detalles.")
            }
            Spacer(modifier = Modifier.width(32.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text("Detalle o vista previa del post seleccionado")
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 1000)
@Composable
fun PreviewHomeScreenExpanded() {
    HomeScreenExpanded()
}
