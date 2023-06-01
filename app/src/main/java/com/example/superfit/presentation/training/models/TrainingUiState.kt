package com.example.superfit.presentation.training.models

import com.example.superfit.domain.model.TrainingType

sealed class TrainingUiState() {
    object Loading : TrainingUiState()
    data class Training(
        val type: TrainingType,
        val count: Int,
        val isDialogOpen: Boolean = true,
        val percent: Float = 1.0f
    ) : TrainingUiState()
    object TrainingSuccess : TrainingUiState()
    data class TrainingUnSuccess(val type: TrainingType, val remains: Int) : TrainingUiState()
}
