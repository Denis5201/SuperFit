package com.example.superfit.domain.usecase

import com.example.superfit.domain.model.UserParameters
import com.example.superfit.domain.repository.ParametersRepository
import javax.inject.Inject

class SaveNewUserParamsUseCase @Inject constructor(
    private val repository: ParametersRepository
) {

    operator fun invoke(userParameters: UserParameters) =
        repository.setNewUserParams(userParameters)
}