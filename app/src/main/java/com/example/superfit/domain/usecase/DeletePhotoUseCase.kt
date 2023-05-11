package com.example.superfit.domain.usecase

import com.example.superfit.domain.repository.PhotoRepository
import javax.inject.Inject

class DeletePhotoUseCase @Inject constructor(
    private val repository: PhotoRepository
) {

    operator fun invoke(photoId: String) = repository.deletePhoto(photoId)
}