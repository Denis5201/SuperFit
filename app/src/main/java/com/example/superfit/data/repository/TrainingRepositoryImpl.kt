package com.example.superfit.data.repository

import android.util.Log
import com.example.superfit.data.api.TrainingApi
import com.example.superfit.data.dto.TrainingDto
import com.example.superfit.domain.model.Training
import com.example.superfit.domain.repository.TrainingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class TrainingRepositoryImpl @Inject constructor(
    private val api: TrainingApi
) : TrainingRepository {

    override fun getTrainingList(): Flow<Result<List<Training>>> = flow {
        try {
            val trainingList = api.getTrainingList().map { it.toTraining() }.sortedBy { it.date }

            emit(Result.success(trainingList))
        } catch (e: Exception) {
            Log.e("OPS getTrainingList", e.message.toString())
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)

    override fun saveNewTraining(training: Training): Flow<Result<Unit>> = flow {
        try {
            api.saveTraining(TrainingDto.fromTraining(training))

            emit(Result.success(Unit))
        } catch (e: Exception) {
            Log.e("OPS saveNewTraining", e.message.toString())
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)

}