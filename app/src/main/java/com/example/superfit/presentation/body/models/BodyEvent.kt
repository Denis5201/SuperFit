package com.example.superfit.presentation.body.models

import android.graphics.Bitmap

sealed class BodyEvent {

    data class NewWeightValue(val value: String, val save: Boolean = false) : BodyEvent()

    data class NewHeightValue(val value: String, val save: Boolean = false) : BodyEvent()

    data class NewPhoto(val image: Bitmap) : BodyEvent()

    data class OpenChangingParamsDialog(val whose: Int) : BodyEvent()

    object CloseChangingParamsDialog : BodyEvent()

    object OpenPhotoDialog : BodyEvent()

    object ClosePhotoDialog : BodyEvent()

    object NavigateToImages : BodyEvent()

    object NavigateToProgress : BodyEvent()

    object NavigateToStatistics : BodyEvent()

    object ErrorShowed : BodyEvent()
}
