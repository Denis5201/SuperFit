package com.example.superfit.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.superfit.presentation.auth.models.SignInAction
import com.example.superfit.presentation.auth.models.SignInEvent
import com.example.superfit.presentation.auth.models.SignInUiState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class SignInViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<SignInUiState>(SignInUiState.Loading)
    val uiState: StateFlow<SignInUiState> = _uiState

    private val _action = Channel<SignInAction?>()
    val action = _action.receiveAsFlow()

    fun getEvent(event: SignInEvent) {
        when(event) {
            is SignInEvent.Navigate -> viewModelScope.launch {
                delay(2000)
                _action.send(SignInAction.Navigate)
            }
            is SignInEvent.AlreadyLoad -> _uiState.value = SignInUiState.ShowLoginInput
        }
    }
}