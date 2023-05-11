package com.example.superfit.presentation.images.models

import com.example.superfit.presentation.UserImage

sealed class ImagesEvent {

    data class ShowImage(val image: UserImage) : ImagesEvent()

    object NavigateBack : ImagesEvent()

    object BackToImageList : ImagesEvent()

    data class OpenDeleteDialog(val galleryIndex: Int, val itemIndex: Int) : ImagesEvent()

    object CloseDeleteDialog : ImagesEvent()

    data class DeletePhoto(val galleryIndex: Int, val itemIndex: Int) : ImagesEvent()

    object ErrorShowed : ImagesEvent()
}
