package com.example.superfit.presentation.training.models

sealed class TrainingAction {
    object NavigateBack : TrainingAction()
    data class ShowError(val message: String) : TrainingAction()
}
