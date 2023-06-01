package com.example.superfit.domain.usecase

import com.example.superfit.domain.model.Training
import com.example.superfit.domain.model.TrainingType
import javax.inject.Inject

class CalculateTrainingPurposeUseCase @Inject constructor() {

    operator fun invoke(list: List<Training>, type: TrainingType): Int {
        return if (list.isEmpty()) {
            when (type) {
                TrainingType.PLANK -> PLANK_START
                TrainingType.RUNNING -> RUNNING_START
                else -> COUNT_START
            }
        } else {
            val maxCount = list.maxOf { it.repeatCount }
            when (type) {
                TrainingType.PLANK -> {
                    if (maxCount < PLANK_START) PLANK_START else (maxCount / 5 + 1) * 5
                }
                TrainingType.RUNNING -> {
                    if (maxCount < RUNNING_START) RUNNING_START else (maxCount / 100 + 1) * 100
                }
                else -> {
                    if (maxCount < COUNT_START) COUNT_START else (maxCount / 5 + 1) * 5
                }
            }
        }
    }

    companion object {
        const val COUNT_START = 10
        const val PLANK_START = 20
        const val RUNNING_START = 1000
    }
}