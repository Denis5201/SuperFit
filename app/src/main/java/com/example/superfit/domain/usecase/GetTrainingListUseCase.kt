package com.example.superfit.domain.usecase

import com.example.superfit.domain.repository.TrainingRepository
import javax.inject.Inject

class GetTrainingListUseCase @Inject constructor(
    private val repository: TrainingRepository
) {

    operator fun invoke() = repository.getTrainingList()
}