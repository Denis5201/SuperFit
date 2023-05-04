package com.example.superfit.presentation.auth.models

sealed class SignUpAction {
    object NavigateToSignIn : SignUpAction()
    data class ShowError(val message: String) : SignUpAction()
}
