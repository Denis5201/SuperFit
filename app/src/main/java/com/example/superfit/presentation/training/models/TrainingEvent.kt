package com.example.superfit.presentation.training.models

sealed class TrainingEvent {

    object CloseStartDialog : TrainingEvent()

    data class DecreaseCount(val decrease: Int) : TrainingEvent()

    object SaveSuccessAndBack : TrainingEvent()

    object ShowSuccess : TrainingEvent()

    data class ShowUnSuccess(val countRemains: Int) : TrainingEvent()

    object BackToMainScreen : TrainingEvent()

    object ErrorShowed : TrainingEvent()
}
