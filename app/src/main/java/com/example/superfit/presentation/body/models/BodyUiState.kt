package com.example.superfit.presentation.body.models

import com.example.superfit.presentation.UserImage

data class BodyUiState(
    val isLoading: Boolean = true,
    val weight: String = "",
    val height: String = "",
    val changingHeight: Boolean = false,
    val changingWeight: Boolean = false,
    val currentChangingText: String = "",
    val choosingImage: Boolean = false,
    val userPhotoProgress: Pair<UserImage?, UserImage?> = Pair(null, null)
)
