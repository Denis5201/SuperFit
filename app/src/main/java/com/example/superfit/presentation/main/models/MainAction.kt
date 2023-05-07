package com.example.superfit.presentation.main.models

sealed class MainAction {
    data class NavigateToBody(val params: String) : MainAction()
    data class NavigateToTraining(val params: String) : MainAction()
    object SignOut : MainAction()
    data class ShowError(val message: String) : MainAction()
}
