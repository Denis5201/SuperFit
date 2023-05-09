package com.example.superfit.domain.usecase

import com.example.superfit.domain.repository.PhotoRepository
import javax.inject.Inject

class GetAllUserPhotoUseCase @Inject constructor(
    private val repository: PhotoRepository
) {

    operator fun invoke() = repository.getAllPhoto()
}