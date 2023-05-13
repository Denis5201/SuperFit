package com.example.superfit.presentation.graphics.models

sealed class StatisticsEvent {

    object BackToMyBody : StatisticsEvent()

    object ErrorShowed : StatisticsEvent()
}
