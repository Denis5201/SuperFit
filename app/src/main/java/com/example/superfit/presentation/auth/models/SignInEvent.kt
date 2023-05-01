package com.example.superfit.presentation.auth.models

sealed class SignInEvent {
    object Navigate : SignInEvent()
    object AlreadyLoad : SignInEvent()
}
