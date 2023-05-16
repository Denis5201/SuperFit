package com.example.superfit.presentation.graphics.models

import com.example.superfit.domain.model.Training
import com.example.superfit.domain.model.TrainingType

sealed class StatisticsUiState {
    object Loading : StatisticsUiState()

    data class ShowCharts(
        val weightData: List<WeightParam> = emptyList(),
        val selectedTrainingType: TrainingType = TrainingType.PUSH_UP,
        val trainingList: List<Training> = emptyList()
    ) : StatisticsUiState()
}
