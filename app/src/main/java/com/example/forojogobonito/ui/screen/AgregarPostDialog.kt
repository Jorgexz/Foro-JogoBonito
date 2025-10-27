package com.example.forojogobonito.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgregarPostDialog(
    onDismiss: () -> Unit,
    // 👇 ya no pedimos "autor" aquí
    onAddPost: (titulo: String, contenido: String, categoria: String, fecha: String) -> Unit
) {
    var titulo by remember { mutableStateOf(TextFieldValue("")) }
    var contenido by remember { mutableStateOf(TextFieldValue("")) }
    var categoria by remember { mutableStateOf(TextFieldValue("")) }

    val fecha = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                onClick = {
                    if (titulo.text.isNotBlank() &&
                        contenido.text.isNotBlank() &&
                        categoria.text.isNotBlank()
                    ) {
                        onAddPost(titulo.text, contenido.text, categoria.text, fecha)
                        onDismiss()
                    }
                }
            ) { Text("Publicar") }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancelar") } },
        title = { Text("Nueva publicación") },
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
                    modifier = Modifier.fillMaxWidth()
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
