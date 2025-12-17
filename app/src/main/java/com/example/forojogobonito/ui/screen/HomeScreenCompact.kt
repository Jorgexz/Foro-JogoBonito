package com.example.forojogobonito.ui.screen

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.forojogobonito.R
import com.example.forojogobonito.model.Post
import com.example.forojogobonito.navigation.AppNavigation
import com.example.forojogobonito.viewmodel.FavoritosViewModel
import com.example.forojogobonito.viewmodel.PostViewModel
import com.example.forojogobonito.viewmodel.UsuarioViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenCompact(
    navController: NavController,
    onLogout: () -> Unit
) {
    val context = LocalContext.current
    val activity = context as ComponentActivity

    // ViewModels
    val postViewModel: PostViewModel = viewModel(activity)
    val usuarioViewModel: UsuarioViewModel = viewModel(activity)
    val favoritosViewModel: FavoritosViewModel = viewModel(activity)

    val posts by postViewModel.posts.collectAsState()
    val currentUser = usuarioViewModel.usuarioActual

    var showDialog by remember { mutableStateOf(false) }
    var postAEditar by remember { mutableStateOf<Post?>(null) }

    // COLOR CORPORATIVO
    val JogaBonitoColor = Color(0xFF01494F)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Foro JogaBonito",
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = Color.White,
                    actionIconContentColor = Color.White
                ),
                actions = {
                    IconButton(onClick = { navController.navigate(AppNavigation.Favoritos.route) }) {
                        Icon(
                            Icons.Default.Favorite,
                            contentDescription = "Ver Favoritos",
                            tint = Color.White
                        )
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface
            ) {
                // 1. INICIO
                NavigationBarItem(
                    selected = true,
                    onClick = { /* Ya estamos aquí */ },
                    label = { Text("Inicio") },
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.cancha),
                            contentDescription = "Inicio",
                            modifier = Modifier.size(24.dp),
                            tint = Color.Unspecified
                        )
                    }
                )

                // 2. PARTIDOS
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate(AppNavigation.Partidos.route) },
                    label = { Text("Partidos") },
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_vs),
                            contentDescription = "Partidos",
                            modifier = Modifier.size(24.dp),
                            tint = Color.Unspecified
                        )
                    }
                )

                // 3. PERFIL
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate(AppNavigation.PerfilResumen.route) },
                    label = { Text("Perfil") },
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_perfil),
                            contentDescription = "Perfil",
                            modifier = Modifier.size(24.dp),
                            tint = Color.Unspecified
                        )
                    }
                )

                // 4. SALIR
                NavigationBarItem(
                    selected = false,
                    onClick = onLogout,
                    label = { Text("Salir") },
                    icon = {
                        Icon(
                            Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = "Salir",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    postAEditar = null
                    showDialog = true
                },
                containerColor = JogaBonitoColor,
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Nuevo Post")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (posts.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = JogaBonitoColor)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    items(posts) { post ->
                        val esMio = (post.usuario_id == currentUser?.id)
                        val soyAdmin = (currentUser?.rol == "admin")
                        val puedoBorrar = esMio || soyAdmin

                        PostCard(
                            post = post,
                            esMio = esMio,
                            puedoBorrar = puedoBorrar,
                            accentColor = JogaBonitoColor,
                            onEdit = {
                                postAEditar = post
                                showDialog = true
                            },
                            onDelete = {
                                postViewModel.eliminarPost(post, currentUser?.id ?: 0)
                            },
                            onFav = {
                                favoritosViewModel.guardarEnFavoritos(post)
                                Toast.makeText(context, "Guardado en Favoritos Offline", Toast.LENGTH_SHORT).show()
                            }
                        )
                    }
                }
            }
        }

        if (showDialog) {
            AgregarPostDialog(
                onDismiss = { showDialog = false },
                postAEditar = postAEditar,
                onAddPost = { titulo, contenido, categoria, fecha ->

                    //RECURSO NATIVO VIBRACIÓN
                    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                    if (vibrator.hasVibrator()) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE))
                        } else {
                            @Suppress("DEPRECATION")
                            vibrator.vibrate(100)
                        }
                    }


                    val userId = currentUser?.id ?: 0
                    if (postAEditar == null) {
                        postViewModel.agregarPost(titulo, contenido, currentUser?.nombre ?: "Anon", categoria, userId)
                    } else {
                        val postEditado = postAEditar!!.copy(
                            titulo = titulo,
                            contenido = contenido,
                            categoria = categoria,
                            fecha = fecha
                        )
                        postViewModel.editarPost(postEditado)
                    }
                    showDialog = false
                }
            )
        }
    }
}

@Composable
fun PostCard(
    post: Post,
    esMio: Boolean,
    puedoBorrar: Boolean,
    accentColor: Color,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onFav: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Avatar
                Image(
                    painter = painterResource(id = R.drawable.ic_perfil),
                    contentDescription = "Avatar",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                )
                Spacer(Modifier.width(8.dp))
                Column {
                    Text(
                        text = post.titulo,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Por ${post.autor} • ${post.fecha}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(Modifier.height(10.dp))
            Text(
                text = post.contenido,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 6,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(Modifier.height(10.dp))

            // Categoria: Fondo Verde, Letra Blanca
            AssistChip(
                onClick = { },
                label = {
                    Text(
                        post.categoria,
                        color = Color.White
                    )
                },
                colors = AssistChipDefaults.assistChipColors(
                    containerColor = accentColor,
                    labelColor = Color.White
                ),
                border = BorderStroke(1.dp, accentColor)
            )

            Spacer(Modifier.height(8.dp))
            HorizontalDivider()
            Spacer(Modifier.height(6.dp))

            // BARRA DE ACCIONES
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onFav) {
                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = "Guardar Favorito",
                        tint = accentColor
                    )
                }

                if (esMio || puedoBorrar) {
                    Row {
                        if (esMio) {
                            TextButton(onClick = onEdit) {
                                Text("Editar", color = accentColor)
                            }
                        }

                        TextButton(onClick = onDelete) {
                            Text("Eliminar", color = MaterialTheme.colorScheme.error)
                        }
                    }
                }
            }
        }
    }
}