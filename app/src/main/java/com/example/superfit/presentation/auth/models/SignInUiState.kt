package com.example.superfit.presentation.auth.models

sealed class SignInUiState {
    object Loading : SignInUiState()
    object ShowLoginInput : SignInUiState()
    object ShowPasswordInput: SignInUiState()
}
