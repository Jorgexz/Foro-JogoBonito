package com.example.forojogobonito

import com.example.forojogobonito.data.remote.ExternalApiService
import com.example.forojogobonito.data.remote.ExternalRetrofitInstance
import com.example.forojogobonito.model.Partido
import com.example.forojogobonito.model.PartidosResponse
import com.example.forojogobonito.viewmodel.PartidosViewModel
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.unmockkAll
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
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PartidosViewModelTest {

    private lateinit var viewModel: PartidosViewModel
    private val apiMock = mockk<ExternalApiService>()
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        // Mockeamos el Singleton de Retrofit
        mockkObject(ExternalRetrofitInstance)
        coEvery { ExternalRetrofitInstance.api } returns apiMock
    }

    @After
    fun tearDown() {
        unmockkAll() // Importante liberar el objeto estático
        Dispatchers.resetMain()
    }

    @Test
    fun `cargarPartidos llena la lista correctamente`() = runTest {
        // GIVEN
        val partido = Partido("1", "Chelsea vs Arsenal", "2025-01-01", "15:00", "Premier", null)
        val respuesta = PartidosResponse(listOf(partido))

        coEvery { apiMock.getProximosPartidos() } returns respuesta

        // WHEN
        viewModel = PartidosViewModel() // Al iniciar llama a cargarPartidos
        advanceUntilIdle()

        // THEN
        assertFalse(viewModel.isLoading.value) // Ya no carga
        assertEquals(1, viewModel.partidos.value.size)
        assertEquals("Chelsea vs Arsenal", viewModel.partidos.value[0].nombrePartido)
    }

    @Test
    fun `cargarPartidos maneja error y deja lista vacia`() = runTest {
        // GIVEN
        coEvery { apiMock.getProximosPartidos() } throws Exception("API Caída")

        // WHEN
        viewModel = PartidosViewModel()
        advanceUntilIdle()

        // THEN
        assertTrue(viewModel.partidos.value.isEmpty())
        assertFalse(viewModel.isLoading.value)
    }
}