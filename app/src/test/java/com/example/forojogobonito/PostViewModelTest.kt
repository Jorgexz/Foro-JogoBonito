package com.example.forojogobonito

import com.example.forojogobonito.model.Post
import com.example.forojogobonito.repository.PostRepository
import com.example.forojogobonito.viewmodel.PostViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PostViewModelTest {

    private lateinit var viewModel: PostViewModel
    private lateinit var repository: PostRepository

    //Herramienta para controlar el tiempo en las corrutinas
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `cargarPosts actualiza la lista exitosamente`() = runTest {
        //Simulamos que AWS nos devuelve 2 posts
        val postsFalsos = listOf(
            Post(id = 1, titulo = "Test 1", contenido = "Hola", autor = "Yo", categoria = "Futbol", fecha = "2025"),
            Post(id = 2, titulo = "Test 2", contenido = "Chao", autor = "Tu", categoria = "Basquet", fecha = "2025")
        )

        //Le enseñamos al Mock qué responder cuando llamen a obtenerPosts()
        coEvery { repository.obtenerPosts() } returns postsFalsos

        //Inicializamos el ViewModel
        viewModel = PostViewModel(repository)

        //Avanzamos el tiempo de la corrutina
        testDispatcher.scheduler.advanceUntilIdle()

        //Leemos los posts del ViewModel
        val postsActuales = viewModel.posts.value

        //Deberían ser los 2 posts falsos
        assertEquals(2, postsActuales.size)
        assertEquals("Test 1", postsActuales[0].titulo)
    }
}