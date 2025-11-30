package com.example.forojogobonito

import com.example.forojogobonito.model.Partido
import com.example.forojogobonito.model.Post
import com.example.forojogobonito.model.Usuario
import com.example.forojogobonito.model.UsuarioErrores
import com.example.forojogobonito.model.UsuarioUiState
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class ModelAndStateTest {

    @Test
    fun `testear modelo Usuario completo`() {
        // Usamos argumentos nombrados para evitar errores
        val user = Usuario(
            id = 1,
            nombre = "Jorge",
            correo = "test@mail.com",
            clave = "123456",
            edad = 25,
            imagen_url = "http://url.com",
            rol = "admin"
        )
        // Al leer las propiedades, marcamos las líneas como "cubiertas"
        assertEquals("Jorge", user.nombre)
        assertEquals("admin", user.rol)
        assertEquals(25, user.edad)
    }

    @Test
    fun `testear modelo Post completo`() {
        val post = Post(
            id = 1,
            titulo = "Titulo",
            contenido = "Contenido",
            autor = "Autor",
            categoria = "Futbol",
            fecha = "2025-01-01",
            usuario_id = 10
        )
        // Probamos el método copy() que genera Kotlin automáticamente
        val postCopia = post.copy(titulo = "Nuevo Titulo")

        assertEquals("Titulo", post.titulo)
        assertEquals("Nuevo Titulo", postCopia.titulo)
    }

    @Test
    fun `testear modelo Partido`() {
        val partido = Partido("1", "A vs B", "Hoy", "12:00", "Liga", null)
        assertEquals("A vs B", partido.nombrePartido)
    }

    @Test
    fun `testear UsuarioUiState y Errores`() {
        // Instanciamos el estado inicial
        val estado = UsuarioUiState()
        assertEquals("", estado.correo)

        // Instanciamos errores
        val errores = UsuarioErrores(nombreError = "Error")
        assertEquals("Error", errores.nombreError)
        assertNull(errores.correoError)

        // Probamos un estado lleno
        val estadoLleno = UsuarioUiState(
            nombre = "J",
            correo = "c",
            clave = "p",
            edad = "20",
            aceptaTerminos = true,
            errores = errores
        )
        assertEquals(true, estadoLleno.aceptaTerminos)
    }
}