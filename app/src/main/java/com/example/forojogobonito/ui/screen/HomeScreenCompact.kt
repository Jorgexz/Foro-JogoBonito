package com.example.forojogobonito.ui.screen

import android.content.Context
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.activity.ComponentActivity
import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
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
import com.example.forojogobonito.model.Post
import com.example.forojogobonito.navigation.AppNavigation
import com.example.forojogobonito.viewmodel.PostViewModel
import com.example.forojogobonito.viewmodel.UsuarioViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun HomeScreenCompact(navController: NavController) {
    val context = LocalContext.current
    val activity = context as ComponentActivity

    //obtenemos ViewModel de Usuario
    val usuarioViewModel: UsuarioViewModel = viewModel(activity)
    //datos usuario ya logueado
    val usuarioActual = usuarioViewModel.usuarioActual

    val postViewModel: PostViewModel = viewModel()
    val posts by postViewModel.posts.collectAsState()

    //estados UI
    var mostrarDialogo by remember { mutableStateOf(false) }

    var postParaEditar by remember { mutableStateOf<Post?>(null) }

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
                    onClick = { },
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
                    postParaEditar = null // Aseguramos que es un post NUEVO
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
                    "¡Operación exitosa!",
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
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No hay publicaciones aún. ¡Sé el primero en comentar!")
                }
            } else {

                @OptIn(ExperimentalFoundationApi::class)
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(
                        items = posts,
                        key = { it.id ?: (it.titulo + it.fecha).hashCode() }
                    ) { post ->

                        // ... dentro de items(posts) ...

                        // LÓGICA DE ADMIN
                        val esMio = (post.usuario_id != null && post.usuario_id == usuarioActual?.id)
                        val soyAdmin = usuarioActual?.rol == "admin"

                        // ¿Tengo permiso para borrar? (Si es mío o soy admin)
                        val tengoPermisos = esMio || soyAdmin

                        Box(Modifier.animateItemPlacement()) {
                            PostCard(
                                post = post,
                                onDelete = {
                                    usuarioActual?.let { user ->
                                        // Al borrar, el backend verificará si tienes permiso real
                                        postViewModel.eliminarPost(post, user.id)
                                    }
                                },
                                onEdit = {
                                    postParaEditar = post
                                },
                                esMio = tengoPermisos // 👈 AQUÍ PASAMOS EL SUPERPODER
                            )
                        }
                    }
                }
            }
        }

        //logica del dialog
        if (mostrarDialogo || postParaEditar != null) {
            AgregarPostDialog(
                onDismiss = {
                    mostrarDialogo = false
                    postParaEditar = null //Limpiamos selección al cerrar
                },
                postAEditar = postParaEditar, //Se lo pasamos al dialog para que se rellene solo
                onAddPost = { titulo, contenido, categoria, _ ->
                    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE))
                    } else {
                        @Suppress("DEPRECATION") vibrator.vibrate(150)
                    }

                    val autor = usuarioActual?.nombre ?: "Anónimo"
                    val idAutor = usuarioActual?.id ?: 0

                    if (postParaEditar != null) {
                        //modo edicion
                        val postEditado = postParaEditar!!.copy(
                            titulo = titulo,
                            contenido = contenido,
                            categoria = categoria
                        )
                        postViewModel.editarPost(postEditado)
                    } else {
                        //modo creacion
                        postViewModel.agregarPost(titulo, contenido, autor, categoria, idAutor)
                    }

                    publicadoOk = true
                    mostrarDialogo = false
                    postParaEditar = null // Reset
                    fabExpandido = false
                }
            )
        }
    }
}

@Composable
fun PostCard(post: Post, onDelete: () -> Unit, onEdit: () -> Unit, esMio: Boolean) {
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
            HorizontalDivider() // Use HorizontalDivider si Divider está deprecado, o Divider() si no
            Spacer(Modifier.height(6.dp))

            //solo muestra si el post es mio
            if (esMio) {
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    //boton editar
                    TextButton(
                        onClick = { onEdit() }
                    ) {
                        Text("Editar")
                    }

                    //boton eliminar
                    TextButton(
                        onClick = { onDelete() }
                    ) {
                        Text(
                            "Eliminar",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }
    }
}