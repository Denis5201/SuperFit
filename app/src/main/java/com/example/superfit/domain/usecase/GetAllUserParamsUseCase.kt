package com.example.superfit.domain.usecase

import com.example.superfit.domain.repository.ParametersRepository
import javax.inject.Inject

class GetAllUserParamsUseCase @Inject constructor(
    private val repository: ParametersRepository
) {

    operator fun invoke() = repository.getUserParamsHistory()
}