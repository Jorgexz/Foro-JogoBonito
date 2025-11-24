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
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
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

        //Mockeamos el objeto Object de Retrofit
        mockkObject(ExternalRetrofitInstance)
        coEvery { ExternalRetrofitInstance.api } returns apiMock
    }

    @After
    fun tearDown() {
        unmockkAll() // Limpiamos los mocks
        Dispatchers.resetMain()
    }

    @Test
    fun `cargarPartidos obtiene lista exitosamente`() = runTest {
        //La API externa responde con 1 partido del Chelsea
        val partidoPrueba = Partido(
            id = "1",
            nombrePartido = "Chelsea vs Arsenal",
            fecha = "2025-12-01",
            hora = "15:00",
            liga = "Premier League",
            imagenUrl = null
        )
        val respuestaFalsa = PartidosResponse(eventos = listOf(partidoPrueba))

        //enseñamos al mock a devolver esa respuesta
        coEvery { apiMock.getProximosPartidos() } returns respuestaFalsa

        //iniciamos el ViewModel que llama a cargarPartidos en el init
        viewModel = PartidosViewModel()
        testDispatcher.scheduler.advanceUntilIdle() //Esperamos a que termine la corrutina

        //entonces la lista del ViewModel debe tener 1 partido
        assertEquals(1, viewModel.partidos.value.size)
        assertEquals("Chelsea vs Arsenal", viewModel.partidos.value[0].nombrePartido)
    }

    @Test
    fun `cargarPartidos maneja errores y deja lista vacia`() = runTest {
        //la API falla y lanza una excepción
        coEvery { apiMock.getProximosPartidos() } throws Exception("Error de API")

        //when
        viewModel = PartidosViewModel()
        testDispatcher.scheduler.advanceUntilIdle()

        //entonces la lista debe estar vacía (no crashea)
        assertEquals(0, viewModel.partidos.value.size)
    }
}