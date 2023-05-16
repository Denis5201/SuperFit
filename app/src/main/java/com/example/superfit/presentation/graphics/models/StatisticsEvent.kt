package com.example.superfit.presentation.graphics.models

import com.example.superfit.domain.model.TrainingType

sealed class StatisticsEvent {

    object BackToMyBody : StatisticsEvent()

    data class SelectTrainingType(val trainingType: TrainingType) : StatisticsEvent()

    object ErrorShowed : StatisticsEvent()
}
