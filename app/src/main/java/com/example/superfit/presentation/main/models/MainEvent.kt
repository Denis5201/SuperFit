package com.example.superfit.presentation.main.models

import com.example.superfit.domain.model.TrainingType

sealed class MainEvent {

    object NavigateToBody : MainEvent()

    object ShowAllExercises : MainEvent()

    object BackToMain : MainEvent()

    data class NavigateToTraining(val type: TrainingType) : MainEvent()

    object SignOut : MainEvent()

    object ErrorShowed : MainEvent()
}
