package com.example.superfit.data.api

import com.example.superfit.data.dto.BodyParameters
import com.example.superfit.data.dto.ProfileDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ParamsApi {

    @GET("api/profile")
    suspend fun getProfile(): ProfileDto

    @POST("api/profile/params")
    suspend fun setParams(@Body bodyParameters: BodyParameters)

    @GET("api/profile/params/history")
    suspend fun paramsHistory(): List<BodyParameters>
}