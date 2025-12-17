package com.example.forojogobonito.ui.screen

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.forojogobonito.viewmodel.FavoritosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritosScreen(navController: NavController) {
    val context = LocalContext.current
    val activity = context as ComponentActivity
    val viewModel: FavoritosViewModel = viewModel(activity)
    val listaFavoritos by viewModel.favoritos.collectAsState()

    Scaffold(
        topBar = { CenterAlignedTopAppBar(title = { Text("Mis Favoritos (Local)") }) }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding).fillMaxSize()) {
            items(listaFavoritos) { fav ->
                Card(
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp).fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = fav.titulo, style = MaterialTheme.typography.titleMedium)
                            Text(text = "Autor: ${fav.autor}", style = MaterialTheme.typography.bodySmall)
                            Text(text = fav.contenido, maxLines = 2)
                        }
                        IconButton(onClick = { viewModel.borrarDeFavoritos(fav) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Borrar", tint = MaterialTheme.colorScheme.error)
                        }
                    }
                }
            }
        }
    }
}