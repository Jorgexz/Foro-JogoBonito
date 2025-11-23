package com.example.forojogobonito

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import org.junit.Rule
import org.junit.Test

class LoginUiTest {

    //abrimos el MainActivity antes de cada test
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun pantallaLogin_muestraElementosCorrectos() {
        // Al iniciar la app, debería salir el Login

        //Buscamos el título Foro Jogabonito
        composeTestRule.onNodeWithText("Foro Jogabonito").assertIsDisplayed()

        //Buscamos el campo de correo
        composeTestRule.onNodeWithText("Correo electrónico").assertIsDisplayed()

        //Buscamos el campo de contraseña
        composeTestRule.onNodeWithText("Contraseña").assertIsDisplayed()

        //Buscamos el boton de entrar
        composeTestRule.onNodeWithText("Entrar al foro").assertIsDisplayed()
    }

    @Test
    fun navegacion_a_registro_funciona() {
        //Estamos en el login
        //Cuando hacemos click en ¿No tienes cuenta?
        composeTestRule.onNodeWithText("¿No tienes cuenta? Regístrate").performClick()

        //Deberíamos ver el titulo de la pantalla de registro
        composeTestRule.onNodeWithText("Crear Cuenta").assertIsDisplayed()
    }
}