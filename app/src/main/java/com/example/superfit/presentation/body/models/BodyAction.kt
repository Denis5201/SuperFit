package com.example.superfit.presentation.body.models

sealed class BodyAction {
    object NavigateToImages : BodyAction()
    object NavigateToProgress : BodyAction()
    object NavigateToStatistics : BodyAction()
    data class ShowError(val message: String) : BodyAction()
}
