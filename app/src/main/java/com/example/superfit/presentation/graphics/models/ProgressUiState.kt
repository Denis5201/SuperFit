package com.example.superfit.presentation.graphics.models

sealed class ProgressUiState {
    object Loading : ProgressUiState()

    data class ShowProgress(
        val pushUps: Pair<Int?, Int?> = Pair(null, null),
        val plank: Pair<Int?, Int?> = Pair(null, null),
        val crunch: Pair<Int?, Int?> = Pair(null, null),
        val squats: Pair<Int?, Int?> = Pair(null, null),
        val running: Pair<Int?, Int?> = Pair(null, null)
    ) : ProgressUiState()
}
