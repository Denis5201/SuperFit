package com.example.superfit.domain.usecase

import com.example.superfit.domain.repository.ParametersRepository
import kotlinx.coroutines.flow.single
import javax.inject.Inject

class IsTokenValidYetUseCase @Inject constructor(
    private val repository: ParametersRepository
) {

    suspend operator fun invoke(): Boolean {
        return repository.getProfile().single().isSuccess
    }
}
