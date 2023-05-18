package com.example.superfit.domain.usecase

import com.example.superfit.domain.model.Training
import com.example.superfit.domain.model.TrainingType
import javax.inject.Inject

class CalculateProgressUseCase @Inject constructor() {

    operator fun invoke(trainingList: List<Training>, type: TrainingType): Pair<Int?, Int?> {
        if (trainingList.isEmpty()) {
            return Pair(null, null)
        }
        val lastTraining = trainingList.lastOrNull { it.exercise == type }
            ?: return Pair(null, null)

        val second = trainingList.lastOrNull { it.exercise == type && it != lastTraining }
            ?: return Pair(lastTraining.repeatCount, null)

        val progress = 100 - lastTraining.repeatCount / second.repeatCount * 100

        return Pair(lastTraining.repeatCount, progress)
    }
}