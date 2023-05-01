package com.example.superfit.di

import com.example.superfit.Constants
import com.example.superfit.data.api.AuthApi
import com.example.superfit.data.api.ParamsApi
import com.example.superfit.data.api.PhotoApi
import com.example.superfit.data.api.TrainingApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideAuthApi(@AuthOkHttpClient okHttpClient: OkHttpClient): AuthApi {
        val authRetrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .client(okHttpClient)
            .build()
        return authRetrofit.create(AuthApi::class.java)
    }

    @Provides
    @Singleton
    fun provideParamsApi(retrofit: Retrofit): ParamsApi = retrofit.create(ParamsApi::class.java)

    @Provides
    @Singleton
    fun providePhotoApi(retrofit: Retrofit): PhotoApi = retrofit.create(PhotoApi::class.java)

    @Provides
    @Singleton
    fun provideTrainingApi(retrofit: Retrofit): TrainingApi = retrofit.create(TrainingApi::class.java)
}