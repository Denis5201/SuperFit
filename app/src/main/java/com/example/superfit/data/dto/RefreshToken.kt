package com.example.superfit.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RefreshToken(
    @SerialName("refresh_token") val refreshToken: String
)
