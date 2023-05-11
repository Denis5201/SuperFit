package com.example.superfit.presentation.images.models

import com.example.superfit.presentation.UserImage

sealed class ImagesUiState {
    object Loading : ImagesUiState()
    data class ShowImageList(
        val galleryItemList: List<List<UserImage>>,
        val isOpenDeleteDialog: Boolean = false,
        val deletingImageIndexes: Pair<Int?, Int?> = Pair(null, null)
        ) : ImagesUiState()
    data class ShowImage(val userImage: UserImage) : ImagesUiState()
}
