package com.example.superfit.presentation.images.models

sealed class ImagesAction {
    object NavigateBack : ImagesAction()
    data class ShowError(val message: String) : ImagesAction()
}
