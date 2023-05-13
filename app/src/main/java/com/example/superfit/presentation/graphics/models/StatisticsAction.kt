package com.example.superfit.presentation.graphics.models

sealed class StatisticsAction {
    object BackToMyBody : StatisticsAction()
    data class ShowError(val message: String) : StatisticsAction()
}
