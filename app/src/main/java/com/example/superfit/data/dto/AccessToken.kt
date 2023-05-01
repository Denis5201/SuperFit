package com.example.superfit.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AccessToken(
    @SerialName("access_token") val accessToken: String,
    val expired: Int
)
