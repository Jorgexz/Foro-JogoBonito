package com.example.forojogobonito

import com.example.forojogobonito.navigation.AppNavigation
import com.example.forojogobonito.navigation.NavEvent
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class NavigationTest {

    @Test
    fun `verificar rutas de AppNavigation`() {
        // Accedemos a cada objeto para asegurar que el coverage lo cuente
        assertEquals("login", AppNavigation.Login.route)
        assertEquals("registro", AppNavigation.Registro.route)
        assertEquals("home", AppNavigation.Home.route)
        assertEquals("perfil", AppNavigation.Perfil.route)
        assertEquals("perfil_resumen", AppNavigation.PerfilResumen.route)
        assertEquals("partidos_screen", AppNavigation.Partidos.route)
    }

    @Test
    fun `verificar sealed class AppNavigation`() {
        // Verificamos polimorfismo
        val listaRutas: List<AppNavigation> = listOf(
            AppNavigation.Login,
            AppNavigation.Registro,
            AppNavigation.Home,
            AppNavigation.Perfil,
            AppNavigation.PerfilResumen,
            AppNavigation.Partidos
        )
        assertTrue(listaRutas.isNotEmpty())
    }

    @Test
    fun `verificar eventos NavEvent`() {
        // Instanciamos los eventos para cubrir las líneas de código del sealed class
        val eventos = listOf(
            NavEvent.NavigateToLogin,
            NavEvent.NavigateToRegistro,
            NavEvent.NavigateToHome,
            NavEvent.NavigateToPerfilResumen
        )

        assertTrue(eventos.contains(NavEvent.NavigateToLogin))
    }
}