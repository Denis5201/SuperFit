package com.example.superfit.data.api

import com.example.superfit.data.dto.ProfilePhoto
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Streaming

interface PhotoApi {

    @GET("api/profile/photos")
    suspend fun getPhotoList(): List<ProfilePhoto>

    @Multipart
    @POST("api/profile/photos")
    suspend fun loadPhoto(@Part file: MultipartBody.Part)

    @Streaming
    @GET("api/profile/photos/{id}")
    suspend fun getPhoto(@Path("id") id: String): ResponseBody

    @DELETE("api/profile/photos/{id}")
    suspend fun deletePhoto(@Path("id") id: String)
}