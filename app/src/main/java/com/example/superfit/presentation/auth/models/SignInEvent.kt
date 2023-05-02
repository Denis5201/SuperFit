package com.example.superfit.presentation.auth.models

sealed class SignInEvent {
    data class InputLogin(val text: String) : SignInEvent()
    data class GoInputPassword(val login: String) : SignInEvent()
    data class InputPassword(val newDigit: String) : SignInEvent()
    object BackToInputLogin : SignInEvent()
    object NavigateToSignUp : SignInEvent()
    object ErrorShowed : SignInEvent()
}
