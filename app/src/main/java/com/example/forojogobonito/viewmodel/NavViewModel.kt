package com.example.forojogobonito.viewmodel

import androidx.lifecycle.ViewModel
import com.example.forojogobonito.navigation.NavEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class NavViewModel : ViewModel() {
    private val _navEvent = MutableStateFlow<NavEvent?>(null)
    val navEvent: StateFlow<NavEvent?> = _navEvent

    fun navigateToLogin() { _navEvent.value = NavEvent.NavigateToLogin }
    fun navigateToRegistro() { _navEvent.value = NavEvent.NavigateToRegistro }
    fun navigateToHome() { _navEvent.value = NavEvent.NavigateToHome }

    fun resetNavEvent() { _navEvent.value = null }
}
