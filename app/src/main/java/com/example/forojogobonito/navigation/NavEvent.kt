package com.example.forojogobonito.navigation

sealed class NavEvent {
    object NavigateToLogin : NavEvent()
    object NavigateToRegistro : NavEvent()
    object NavigateToHome : NavEvent()
    object NavigateToPerfilResumen : NavEvent()
}
