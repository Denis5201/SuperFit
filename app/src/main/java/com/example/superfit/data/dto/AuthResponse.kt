package com.example.superfit.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val username: String,
    @SerialName("refresh_token") val refreshToken: String,
    val expired: Long
)
