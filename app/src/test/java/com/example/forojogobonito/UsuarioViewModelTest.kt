package com.example.forojogobonito

import com.example.forojogobonito.model.Usuario
import com.example.forojogobonito.repository.UsuarioRepository
import com.example.forojogobonito.viewmodel.UsuarioViewModel
import io.mockk.coEvery
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
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UsuarioViewModelTest {

    private lateinit var viewModel: UsuarioViewModel
    private lateinit var repository: UsuarioRepository
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk(relaxed = true)
        viewModel = UsuarioViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }


    // 1. TESTS DE INPUTS (onChange)


    @Test
    fun `inputs actualizan el estado UI`() {
        viewModel.onNombreChange("Jorge")
        assertEquals("Jorge", viewModel.uiState.value.nombre)

        viewModel.onCorreoChange("jorge@mail.com")
        assertEquals("jorge@mail.com", viewModel.uiState.value.correo)

        viewModel.onClaveChange("123456")
        assertEquals("123456", viewModel.uiState.value.clave)

        viewModel.onEdadChange("25")
        assertEquals("25", viewModel.uiState.value.edad)
    }


    // 2. TESTS DE VALIDACION (CUBRIENDO TODOS LOS IFs)

    @Test
    fun `validar falla si hay campos vacios`() {
        // Dejamos todo vacío por defecto
        val esValido = viewModel.validarFormulario()

        assertFalse(esValido)
        assertNotNull(viewModel.uiState.value.errores.nombreError)
        assertNotNull(viewModel.uiState.value.errores.correoError)
        assertNotNull(viewModel.uiState.value.errores.claveError)
        assertNotNull(viewModel.uiState.value.errores.edadError)
    }

    @Test
    fun `validar falla si correo no tiene formato valido`() {
        viewModel.onNombreChange("Jorge")
        viewModel.onCorreoChange("esto-no-es-un-correo") // Falla aquí
        viewModel.onClaveChange("123456")
        viewModel.onEdadChange("20")

        val esValido = viewModel.validarFormulario()

        assertFalse(esValido)
        assertEquals("Ingresa un correo válido (ej: nombre@gmail.com)", viewModel.uiState.value.errores.correoError)
    }

    @Test
    fun `validar falla si clave es muy corta`() {
        viewModel.onNombreChange("Jorge")
        viewModel.onCorreoChange("jorge@mail.com")
        viewModel.onClaveChange("123") // Menos de 6 caracteres
        viewModel.onEdadChange("20")

        val esValido = viewModel.validarFormulario()

        assertFalse(esValido)
        assertEquals("La contraseña debe tener al menos 6 caracteres", viewModel.uiState.value.errores.claveError)
    }

    @Test
    fun `validar falla si edad no es numero`() {
        viewModel.onNombreChange("Jorge")
        viewModel.onCorreoChange("jorge@mail.com")
        viewModel.onClaveChange("123456")
        viewModel.onEdadChange("veinte") // No es número

        val esValido = viewModel.validarFormulario()

        assertFalse(esValido)
        assertEquals("Ingresa un número", viewModel.uiState.value.errores.edadError)
    }

    

    @Test
    fun `validar pasa exitosamente con datos correctos`() {
        viewModel.onNombreChange("Jorge")
        viewModel.onCorreoChange("jorge@mail.com")
        viewModel.onClaveChange("123456")
        viewModel.onEdadChange("25")

        val esValido = viewModel.validarFormulario()

        assertTrue(esValido)
        assertNull(viewModel.uiState.value.errores.nombreError)
        assertNull(viewModel.uiState.value.errores.correoError)
    }


    // 3. TESTS DE LOGIN (ASINCRONO)


    @Test
    fun `login exitoso actualiza usuario y llama onSuccess`() = runTest {
        val userMock = Usuario(1, "Jorge", "j@j.com", "123", 25, rol = "admin")
        coEvery { repository.login(any(), any()) } returns userMock

        viewModel.onCorreoChange("j@j.com")
        viewModel.onClaveChange("123")

        var successCalled = false
        viewModel.login(
            onSuccess = { successCalled = true },
            onError = {}
        )
        advanceUntilIdle()

        assertTrue(successCalled)
        assertEquals("Jorge", viewModel.usuarioActual?.nombre)
        // Verificamos que el nombre en UI State se actualizó
        assertEquals("Jorge", viewModel.uiState.value.nombre)
    }

    @Test
    fun `login fallido llama onError`() = runTest {
        coEvery { repository.login(any(), any()) } throws Exception("Error Login")

        var errorCalled = false
        viewModel.login(
            onSuccess = {},
            onError = { errorCalled = true }
        )
        advanceUntilIdle()

        assertTrue(errorCalled)
    }

    // ==========================================
    // 4. TESTS DE REGISTRO (ASINCRONO)
    // ==========================================

    @Test
    fun `registrarUsuario exitoso actualiza usuario y llama onSuccess`() = runTest {
        val userMock = Usuario(2, "Nuevo", "n@n.com", "123", 30, rol = "user")
        coEvery { repository.registro(any(), any(), any(), any()) } returns userMock

        // Configuramos estado para pasar validación interna si la hubiera
        viewModel.onNombreChange("Nuevo")

        var successCalled = false
        viewModel.registrarUsuario(
            onSuccess = { successCalled = true },
            onError = {}
        )
        advanceUntilIdle()

        assertTrue(successCalled)
        assertEquals("Nuevo", viewModel.usuarioActual?.nombre)
    }

    @Test
    fun `registrarUsuario fallido llama onError`() = runTest {
        coEvery { repository.registro(any(), any(), any(), any()) } throws Exception("Error Registro")

        var errorCalled = false
        viewModel.registrarUsuario(
            onSuccess = {},
            onError = { errorCalled = true }
        )
        advanceUntilIdle()

        assertTrue(errorCalled)
    }
}