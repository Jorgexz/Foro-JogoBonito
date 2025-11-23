package com.example.forojogobonito

import com.example.forojogobonito.repository.UsuarioRepository
import com.example.forojogobonito.viewmodel.UsuarioViewModel
import io.mockk.mockk
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Test

class UsuarioViewModelTest {

    private lateinit var viewModel: UsuarioViewModel
    private lateinit var repository: UsuarioRepository

    @Before
    fun setup() {
        //Creamos un repositorio falso (Mock) para no llamar a AWS
        repository = mockk()
        //Inicializamos el ViewModel con ese repo falso
        viewModel = UsuarioViewModel(repository)
    }

    @Test
    fun `validarFormulario retorna FALSE si el correo esta vacio`() {
        viewModel.onNombreChange("Jorge")
        viewModel.onCorreoChange("") //Vacío para que tire error
        viewModel.onClaveChange("123456")
        viewModel.onEdadChange("25")

        //Ejecutamos la validación
        val resultado = viewModel.validarFormulario()

        //Entonces El resultado debe ser Falso
        assertFalse("El formulario no deberia ser valido sin correo", resultado)
    }

    @Test
    fun `validarFormulario retorna FALSE si la clave es corta`() {
        //Correo bien, pero clave de 3 números
        viewModel.onNombreChange("Jorge")
        viewModel.onCorreoChange("jorge@duoc.cl")
        viewModel.onClaveChange("123") // Muy corta
        viewModel.onEdadChange("25")

        //Ejecutamos
        val resultado = viewModel.validarFormulario()

        //Nuevamente es falso
        assertFalse("Debería fallar con clave corta", resultado)
    }

    @Test
    fun `validarFormulario retorna TRUE si todo esta correcto`() {
        //todo perfecto
        viewModel.onNombreChange("Jorge")
        viewModel.onCorreoChange("jorge@duoc.cl")
        viewModel.onClaveChange("123456")
        viewModel.onEdadChange("25")

        val resultado = viewModel.validarFormulario()

        assertTrue("El formulario debería ser válido", resultado)
    }
}