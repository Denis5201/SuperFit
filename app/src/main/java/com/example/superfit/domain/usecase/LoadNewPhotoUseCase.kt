package com.example.superfit.domain.usecase

import com.example.superfit.domain.repository.PhotoRepository
import javax.inject.Inject

class LoadNewPhotoUseCase @Inject constructor(
    private val repository: PhotoRepository
) {

    operator fun invoke(image: ByteArray) = repository.loadNewPhoto(image)
}