package com.example.superfit.di

import com.example.superfit.Constants
import com.example.superfit.data.AuthInterceptor
import com.example.superfit.data.Authenticator
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @AuthOkHttpClient
    @Singleton
    @Provides
    fun provideAuthHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            val logLevel = HttpLoggingInterceptor.Level.BODY
            addInterceptor(HttpLoggingInterceptor().setLevel(logLevel))
        }.build()
    }

    @CommonOkHttpClient
    @Singleton
    @Provides
    fun provideCommonHttpClient(
        authInterceptor: AuthInterceptor,
        authenticator: Authenticator
    ): OkHttpClient {
        return OkHttpClient.Builder().apply {
            val logLevel = HttpLoggingInterceptor.Level.BODY
            addInterceptor(HttpLoggingInterceptor().setLevel(logLevel))
            addInterceptor(authInterceptor)
            authenticator(authenticator)
        }.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(@CommonOkHttpClient okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .client(okHttpClient)
            .build()
    }
}