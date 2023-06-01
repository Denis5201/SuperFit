package com.example.superfit.domain.usecase

import com.example.superfit.domain.model.Training
import com.example.superfit.domain.repository.TrainingRepository
import javax.inject.Inject

class SaveTrainingResultUseCase @Inject constructor(
    private val repository: TrainingRepository
) {

    operator fun invoke(training: Training) = repository.saveNewTraining(training)
}