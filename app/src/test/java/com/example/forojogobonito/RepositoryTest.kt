package com.example.forojogobonito

import com.example.forojogobonito.data.remote.ApiService
import com.example.forojogobonito.data.remote.RetrofitInstance
import com.example.forojogobonito.model.Post
import com.example.forojogobonito.model.Usuario
import com.example.forojogobonito.repository.PostRepository
import com.example.forojogobonito.repository.UsuarioRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.unmockkAll
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class RepositoryTest {

    private val apiMock = mockk<ApiService>(relaxed = true)
    private lateinit var postRepository: PostRepository
    private lateinit var usuarioRepository: UsuarioRepository

    @Before
    fun setup() {
        // Mockeamos el objeto Singleton RetrofitInstance
        mockkObject(RetrofitInstance)
        coEvery { RetrofitInstance.api } returns apiMock

        postRepository = PostRepository()
        usuarioRepository = UsuarioRepository()
    }

    @After
    fun tearDown() {
        unmockkAll() // Importante: Limpiar el mock del objeto estático
    }

    // ==========================================
    // TESTS DE USUARIO REPOSITORY
    // ==========================================

    @Test
    fun `login llama a la api correctamente`() = runTest {
        // GIVEN
        val usuarioEsperado = Usuario(1, "Jorge", "j@j.com", "123", 25, rol = "admin")
        coEvery { apiMock.login(any()) } returns usuarioEsperado

        // WHEN
        val resultado = usuarioRepository.login("j@j.com", "123")

        // THEN
        assertEquals("Jorge", resultado.nombre)
        coVerify { apiMock.login(match { it.correo == "j@j.com" && it.clave == "123" }) }
    }

    @Test
    fun `registro convierte la edad de String a Int correctamente`() = runTest {
        // GIVEN
        val usuarioNuevo = Usuario(2, "Nuevo", "n@n.com", "123", 20)
        coEvery { apiMock.registro(any()) } returns usuarioNuevo

        // WHEN
        // Pasamos la edad como String "20"
        usuarioRepository.registro("Nuevo", "n@n.com", "123", "20")

        // THEN
        // Verificamos que al API le llegó un INT 20, no un String
        coVerify {
            apiMock.registro(match { it.edad == 20 })
        }
    }

    @Test
    fun `registro maneja edad vacia como null`() = runTest {
        // GIVEN
        coEvery { apiMock.registro(any()) } returns Usuario(0,"","","",null)

        // WHEN
        usuarioRepository.registro("Nuevo", "n@n.com", "123", "") // Edad vacía

        // THEN
        coVerify {
            apiMock.registro(match { it.edad == null })
        }
    }

    // ==========================================
    // TESTS DE POST REPOSITORY
    // ==========================================

    @Test
    fun `obtenerPosts retorna lista de la api`() = runTest {
        val lista = listOf(Post(1, "T", "C", "A", "C", "F", 1))
        coEvery { apiMock.getPosts() } returns lista

        val resultado = postRepository.obtenerPosts()
        assertEquals(1, resultado.size)
    }

    @Test
    fun `eliminarPost lanza excepcion si la api retorna error`() = runTest {
        // GIVEN: Simulamos error 403
        // CORRECCION: Usamos Response<Unit> en lugar de Response<Void>
        val responseError = Response.error<Unit>(403, ResponseBody.create(null, ""))

        coEvery { apiMock.eliminarPost(any(), any()) } returns responseError

        // WHEN / THEN
        try {
            postRepository.eliminarPost(1, 1)
            assert(false) { "Debería haber lanzado excepción" }
        } catch (e: Exception) {
            assert(e.message!!.contains("Error al borrar"))
        }
    }

    @Test
    fun `eliminarPost no lanza excepcion si es exitoso`() = runTest {
        // GIVEN: Simulamos éxito 200 OK
        // CORRECCION: Usamos Response.success(Unit)
        val responseOk = Response.success(Unit)

        coEvery { apiMock.eliminarPost(any(), any()) } returns responseOk

        try {
            postRepository.eliminarPost(1, 1)
            assert(true) // Pasó
        } catch (e: Exception) {
            assert(false) { "No debería fallar" }
        }
    }

    @Test
    fun `editarPost llama a la api si el post tiene ID`() = runTest {
        val post = Post(id = 5, titulo = "T", contenido = "C", autor = "A", categoria = "C", fecha = "F", usuario_id = 1)

        // CORRECCION: Usamos Response.success(Unit)
        coEvery { apiMock.editarPost(any(), any()) } returns Response.success(Unit)

        postRepository.editarPost(post)

        coVerify { apiMock.editarPost(5, post) }
    }
}