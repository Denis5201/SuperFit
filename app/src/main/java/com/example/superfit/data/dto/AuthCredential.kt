package com.example.superfit.data.dto

import com.example.superfit.domain.model.Credentials
import kotlinx.serialization.Serializable

@Serializable
data class AuthCredential(
    val login: String,
    val password: String
) {
    companion object {
        fun fromCredentials(credentials: Credentials): AuthCredential {
            return AuthCredential(
                login = credentials.login,
                password = credentials.password
            )
        }
    }
}
