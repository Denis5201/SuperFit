package com.example.superfit.presentation.graphics.models

sealed class ProgressAction {
    object BackToMyBody : ProgressAction()
    data class ShowError(val message: String) : ProgressAction()
}
