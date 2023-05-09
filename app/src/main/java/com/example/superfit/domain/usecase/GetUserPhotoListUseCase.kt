package com.example.superfit.domain.usecase

import com.example.superfit.domain.repository.PhotoRepository
import javax.inject.Inject

class GetUserPhotoListUseCase @Inject constructor(
    private val repository: PhotoRepository
) {

    operator fun invoke() = repository.getUserPhotoList()
}