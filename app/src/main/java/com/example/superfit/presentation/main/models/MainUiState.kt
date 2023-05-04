package com.example.superfit.presentation.main.models

import com.example.superfit.domain.model.TrainingType

sealed class MainUiState {
    object Loading : MainUiState()
    data class ShowMain(
        val weight: Int?,
        val height: Int?,
        val lastExercises: Pair<TrainingType?, TrainingType?>
        ) : MainUiState()
    object ShowAllExercises : MainUiState()
}
