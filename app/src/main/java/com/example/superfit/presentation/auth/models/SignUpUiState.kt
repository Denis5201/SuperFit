package com.example.superfit.presentation.auth.models

data class SignUpUiState(
    val userName: String = "",
    val email: String = "",
    val code: String = "",
    val repeatCode: String = ""
)
