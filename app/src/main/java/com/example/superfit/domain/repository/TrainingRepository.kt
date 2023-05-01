package com.example.superfit.domain.repository

import com.example.superfit.domain.model.Training
import kotlinx.coroutines.flow.Flow

interface TrainingRepository {

    fun getTrainingList(): Flow<Result<List<Training>>>

    fun saveNewTraining(training: Training): Flow<Result<Unit>>
}