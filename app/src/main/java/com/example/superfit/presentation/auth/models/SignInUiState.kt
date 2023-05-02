package com.example.superfit.presentation.auth.models

sealed class SignInUiState {
    object Loading : SignInUiState()
    data class ShowLoginInput(val userName: String) : SignInUiState()
    object ShowPasswordInput: SignInUiState()
}
