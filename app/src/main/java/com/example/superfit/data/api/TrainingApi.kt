package com.example.superfit.data.api

import com.example.superfit.data.dto.TrainingDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TrainingApi {

    @GET("api/trainings")
    suspend fun getTrainingList(): List<TrainingDto>

    @POST("api/trainings")
    suspend fun saveTraining(@Body trainingDto: TrainingDto)
}