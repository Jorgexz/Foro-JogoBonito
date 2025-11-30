package com.example.forojogobonito

import com.example.forojogobonito.navigation.NavEvent
import com.example.forojogobonito.viewmodel.NavViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class NavViewModelTest {

    @Test
    fun `NavViewModel inicia con estado nulo`() {
        val viewModel = NavViewModel()
        assertNull(viewModel.navEvent.value)
    }

    @Test
    fun `MapsToLogin actualiza el estado correctamente`() {
        val viewModel = NavViewModel()
        viewModel.navigateToLogin()
        assertEquals(NavEvent.NavigateToLogin, viewModel.navEvent.value)
    }

    @Test
    fun `MapsToRegistro actualiza el estado correctamente`() {
        val viewModel = NavViewModel()
        viewModel.navigateToRegistro()
        assertEquals(NavEvent.NavigateToRegistro, viewModel.navEvent.value)
    }

    @Test
    fun `MapsToHome actualiza el estado correctamente`() {
        val viewModel = NavViewModel()
        viewModel.navigateToHome()
        assertEquals(NavEvent.NavigateToHome, viewModel.navEvent.value)
    }

    @Test
    fun `resetNavEvent devuelve el estado a nulo`() {
        val viewModel = NavViewModel()

        // Primero navegamos a algo
        viewModel.navigateToHome()
        assertEquals(NavEvent.NavigateToHome, viewModel.navEvent.value)

        // Ahora reseteamos
        viewModel.resetNavEvent()
        assertNull(viewModel.navEvent.value)
    }
}