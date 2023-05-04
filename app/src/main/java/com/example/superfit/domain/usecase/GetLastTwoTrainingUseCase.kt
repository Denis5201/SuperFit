package com.example.superfit.domain.usecase

import com.example.superfit.domain.model.Training
import com.example.superfit.domain.model.TrainingType
import javax.inject.Inject

class GetLastTwoTrainingUseCase @Inject constructor() {

    operator fun invoke(trainingList: List<Training>): Pair<TrainingType?, TrainingType?> {
        if (trainingList.isEmpty()) {
            return Pair(null, null)
        }
        val firstLast = trainingList.last().exercise
        val secondLast = trainingList.lastOrNull { it.exercise != firstLast }?.exercise

        return Pair(firstLast, secondLast)
    }
}