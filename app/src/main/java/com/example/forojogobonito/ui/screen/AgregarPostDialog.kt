package com.example.forojogobonito.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.forojogobonito.model.Post //Asegúrate de importar tu modelo Post
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgregarPostDialog(
    onDismiss: () -> Unit,
    onAddPost: (titulo: String, contenido: String, categoria: String, fecha: String) -> Unit,
    postAEditar: Post? = null
) {
    //Inicializamos, si hay postAEditar, usamos sus datos; si no, cadena vacía.
    var titulo by remember { mutableStateOf(postAEditar?.titulo ?: "") }
    var contenido by remember { mutableStateOf(postAEditar?.contenido ?: "") }
    var categoria by remember { mutableStateOf(postAEditar?.categoria ?: "") }

    //Generamos la fecha actual
    val fecha = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                onClick = {
                    if (titulo.isNotBlank() && contenido.isNotBlank() && categoria.isNotBlank()) {
                        //Enviamos los datos a HomeScreen
                        onAddPost(titulo, contenido, categoria, fecha)
                        onDismiss()
                    }
                }
            ) {

                Text(if (postAEditar != null) "Guardar Cambios" else "Publicar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancelar") }
        },

        title = {
            Text(if (postAEditar != null) "Editar Publicación" else "Nueva Publicación")
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = titulo,
                    onValueChange = { titulo = it },
                    label = { Text("Título") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = contenido,
                    onValueChange = { contenido = it },
                    label = { Text("Contenido") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 5 // Un poco más de espacio para escribir
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = categoria,
                    onValueChange = { categoria = it },
                    label = { Text("Etiqueta (ej: Partidos, Ligas)") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    )
}