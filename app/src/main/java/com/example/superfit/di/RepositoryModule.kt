package com.example.superfit.di

import com.example.superfit.data.repository.AuthRepositoryImpl
import com.example.superfit.data.repository.LocalSettingsImpl
import com.example.superfit.data.repository.ParametersRepositoryImpl
import com.example.superfit.data.repository.PhotoRepositoryImpl
import com.example.superfit.data.repository.TrainingRepositoryImpl
import com.example.superfit.domain.repository.AuthRepository
import com.example.superfit.domain.repository.LocalSettings
import com.example.superfit.domain.repository.ParametersRepository
import com.example.superfit.domain.repository.PhotoRepository
import com.example.superfit.domain.repository.TrainingRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindParametersRepository(
        parametersRepositoryImpl: ParametersRepositoryImpl
    ): ParametersRepository

    @Binds
    @Singleton
    abstract fun bindPhotoRepository(photoRepositoryImpl: PhotoRepositoryImpl): PhotoRepository

    @Binds
    @Singleton
    abstract fun bindTrainingRepository(
        trainingRepositoryImpl: TrainingRepositoryImpl
    ): TrainingRepository

    @Binds
    @Singleton
    abstract fun bindLocalSettings(localSettingsImpl: LocalSettingsImpl): LocalSettings
}