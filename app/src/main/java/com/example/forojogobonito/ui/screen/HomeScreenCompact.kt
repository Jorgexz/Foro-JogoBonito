package com.example.forojogobonito.ui.screen

import android.content.Context
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.activity.ComponentActivity
import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi // ✅ Import necesario
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.forojogobonito.R
import com.example.forojogobonito.data.Post
import com.example.forojogobonito.navigation.AppNavigation
import com.example.forojogobonito.viewmodel.PostViewModel
import com.example.forojogobonito.viewmodel.UsuarioViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun HomeScreenCompact(navController: NavController) {
    val context = LocalContext.current
    val activity = context as ComponentActivity

    val usuarioViewModel: UsuarioViewModel = viewModel(activity)
    val uiUsuario by usuarioViewModel.uiState.collectAsState()

    val postViewModel: PostViewModel = viewModel(
        factory = androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory(
            context.applicationContext as android.app.Application
        )
    )
    val posts by postViewModel.posts.collectAsState()

    var mostrarDialogo by remember { mutableStateOf(false) }
    var publicadoOk by remember { mutableStateOf(false) }
    var fabExpandido by remember { mutableStateOf(false) }
    val rot by animateFloatAsState(if (fabExpandido) 45f else 0f, label = "fab-rotation")

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(publicadoOk) {
        if (publicadoOk) {
            kotlinx.coroutines.delay(1600)
            publicadoOk = false
        }
    }

    Scaffold(
        topBar = {
            val LOGO_SIZE = 72.dp
            LargeTopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Foro Jogabonito", style = MaterialTheme.typography.headlineLarge)
                            Spacer(Modifier.height(6.dp))
                            Image(
                                painter = painterResource(id = R.drawable.pelotafutbol),
                                contentDescription = "Logo del foro",
                                modifier = Modifier.size(LOGO_SIZE)
                            )
                        }
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = true,
                    onClick = { /* inicio */ },
                    label = { Text("Inicio") },
                    icon = {
                        Image(
                            painter = painterResource(id = R.drawable.cancha),
                            contentDescription = "Inicio",
                            modifier = Modifier.size(32.dp).padding(2.dp)
                        )
                    }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate(AppNavigation.Partidos.route) },
                    label = { Text("Partidos") },
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_vs),
                            contentDescription = "Partidos",
                            tint = Color.Unspecified,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate(AppNavigation.PerfilResumen.route) },
                    label = { Text("Perfil") },
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.ic_perfil),
                            contentDescription = "Perfil",
                            tint = Color.Unspecified,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = {
                        navController.navigate(AppNavigation.Login.route) {
                            popUpTo(AppNavigation.Home.route) { inclusive = true }
                        }
                    },
                    label = { Text("Salir") },
                    icon = {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = "Salir"
                        )
                    }
                )
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    fabExpandido = !fabExpandido
                    mostrarDialogo = true
                }
            ) {
                Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.rotate(rot))
            }
        }
    ) { paddingValues ->

        AnimatedVisibility(
            visible = publicadoOk,
            enter = slideInVertically { -it } + fadeIn(),
            exit = slideOutVertically { -it } + fadeOut(),
        ) {
            Surface(
                color = MaterialTheme.colorScheme.primaryContainer,
                tonalElevation = 3.dp,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
            ) {
                Text(
                    "¡Publicación creada!",
                    modifier = Modifier.padding(12.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            if (posts.isEmpty()) {
                Text("No hay publicaciones aún. ¡Sé el primero en comentar!")
            } else {

                // ✅ Aquí está la corrección
                @OptIn(ExperimentalFoundationApi::class)
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(
                        items = posts,
                        key = { it.id }
                    ) { post ->
                        Box(Modifier.animateItemPlacement()) {
                            PostCard(
                                post = post,
                                onDelete = { postViewModel.eliminarPost(post) }
                            )
                        }
                    }
                }
            }
        }

        if (mostrarDialogo) {
            AgregarPostDialog(
                onDismiss = { mostrarDialogo = false },
                onAddPost = { titulo, contenido, categoria, fecha ->
                    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE))
                    } else {
                        @Suppress("DEPRECATION") vibrator.vibrate(150)
                    }

                    val autor = uiUsuario.nombre.ifBlank { "Anónimo" }
                    postViewModel.agregarPost(titulo, contenido, autor, categoria)
                    publicadoOk = true
                    mostrarDialogo = false
                    fabExpandido = false
                }
            )
        }
    }
}
@Composable
fun PostCard(post: Post, onDelete: () -> Unit) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(Modifier.padding(14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                val inicial = (post.autor.firstOrNull() ?: '•').uppercaseChar().toString()
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = inicial,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Black)
                    )
                }
                Spacer(Modifier.width(10.dp))
                Column(Modifier.weight(1f)) {
                    Text(
                        text = post.titulo,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(Modifier.height(2.dp))
                    Text(
                        text = "por ${post.autor} • ${post.fecha}",
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
            AssistChip(onClick = { }, label = { Text(post.categoria) })
            Spacer(Modifier.height(8.dp))
            Divider()
            Spacer(Modifier.height(6.dp))
            Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                TextButton(onClick = onDelete) {
                    Text("Eliminar", color = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
}
