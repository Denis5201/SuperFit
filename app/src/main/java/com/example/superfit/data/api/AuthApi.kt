package com.example.superfit.data.api

import com.example.superfit.data.dto.AccessToken
import com.example.superfit.data.dto.AuthCredential
import com.example.superfit.data.dto.AuthResponse
import com.example.superfit.data.dto.RefreshToken
import com.example.superfit.data.dto.RegistrationBody
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("api/auth/token")
    suspend fun login(@Body authCredential: AuthCredential): AuthResponse

    @POST("api/auth/token/refresh")
    suspend fun getAccessToken(@Body refreshToken: RefreshToken): AccessToken

    @POST("api/auth/register")
    suspend fun registration(@Body registrationBody: RegistrationBody)
}