package com.example.superfit.presentation.auth.models

sealed class SignInAction {
    object NavigateToSignUp: SignInAction()
    object NavigateToMain: SignInAction()
    data class ShowError(val message: String): SignInAction()
}
