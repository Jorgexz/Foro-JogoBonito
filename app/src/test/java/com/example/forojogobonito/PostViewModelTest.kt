package com.example.forojogobonito

import com.example.forojogobonito.model.Post
import com.example.forojogobonito.repository.PostRepository
import com.example.forojogobonito.viewmodel.PostViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PostViewModelTest {

    private lateinit var viewModel: PostViewModel
    private lateinit var repository: PostRepository
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk(relaxed = true)
        coEvery { repository.obtenerPosts() } returns emptyList()
        viewModel = PostViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }



    @Test
    fun `cargarPosts actualiza lista cuando repositorio devuelve datos`() = runTest {
        val listaMock = listOf(
            Post(1, "Titulo", "C", "A", "C", "2025-01-01", 1)
        )
        coEvery { repository.obtenerPosts() } returns listaMock

        viewModel.cargarPosts()
        advanceUntilIdle()

        assertEquals(1, viewModel.posts.value.size)
    }

    @Test
    fun `agregarPost llama al repositorio y recarga`() = runTest {
        val post = Post(
            titulo = "T",
            contenido = "C",
            autor = "A",
            categoria = "C",
            fecha = "2025-01-01",
            usuario_id = 1
        )
        coEvery { repository.crearPost(any()) } returns post

        viewModel.agregarPost("T", "C", "A", "C", 1)
        advanceUntilIdle()

        coVerify { repository.crearPost(any()) }
        coVerify(atLeast = 2) { repository.obtenerPosts() }
    }

    @Test
    fun `eliminarPost llama al repositorio`() = runTest {
        val post = Post(id=1, titulo="T", contenido="C", autor="A", categoria="C", fecha="F", usuario_id=1)

        viewModel.eliminarPost(post, 1)
        advanceUntilIdle()

        coVerify { repository.eliminarPost(1, 1) }
    }

    @Test
    fun `editarPost llama al repositorio`() = runTest {
        val post = Post(id=1, titulo="Edit", contenido="C", autor="A", categoria="C", fecha="F", usuario_id=1)

        viewModel.editarPost(post)
        advanceUntilIdle()

        coVerify { repository.editarPost(any()) }
    }



    @Test
    fun `cargarPosts maneja excepcion silenciosamente`() = runTest {
        coEvery { repository.obtenerPosts() } throws Exception("Error API")

        viewModel.cargarPosts()
        advanceUntilIdle()

        assertTrue(viewModel.posts.value.isEmpty())
    }

    @Test
    fun `agregarPost maneja excepcion del repositorio`() = runTest {
        coEvery { repository.crearPost(any()) } throws Exception("Error Crear")

        viewModel.agregarPost("T", "C", "A", "C", 1)
        advanceUntilIdle()

        coVerify { repository.crearPost(any()) }
    }

    @Test
    fun `eliminarPost maneja excepcion y no crashea`() = runTest {

        val post = Post(id=1, titulo="T", contenido="C", autor="A", categoria="C", fecha="F", usuario_id=1)
        coEvery { repository.eliminarPost(any(), any()) } throws Exception("Error Borrar")

        viewModel.eliminarPost(post, 1)
        advanceUntilIdle()

        coVerify { repository.eliminarPost(1, 1) }
    }

    @Test
    fun `editarPost maneja excepcion y no crashea`() = runTest {

        val post = Post(id=1, titulo="Edit", contenido="C", autor="A", categoria="C", fecha="F", usuario_id=1)
        coEvery { repository.editarPost(any()) } throws Exception("Error Editar")

        viewModel.editarPost(post)
        advanceUntilIdle()

        coVerify { repository.editarPost(any()) }
    }
}