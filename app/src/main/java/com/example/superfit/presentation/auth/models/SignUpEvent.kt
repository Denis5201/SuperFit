package com.example.superfit.presentation.auth.models

sealed class SignUpEvent{
    data class InputUserName(val text: String) : SignUpEvent()

    data class InputEmail(val text: String) : SignUpEvent()

    data class InputCode(val text: String) : SignUpEvent()

    data class InputRepeatCode(val text: String) : SignUpEvent()

    object SignUp : SignUpEvent()

    object NavigateToSignIn : SignUpEvent()

    object ErrorShowed : SignUpEvent()
}
