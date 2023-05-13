package com.example.superfit.presentation.graphics.models

sealed class ProgressEvent {

    object BackToMyBody : ProgressEvent()

    object ErrorShowed : ProgressEvent()
}
