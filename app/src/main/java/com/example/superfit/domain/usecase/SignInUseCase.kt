package com.example.superfit.domain.usecase

import com.example.superfit.domain.model.Credentials
import com.example.superfit.domain.repository.AuthRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val repository: AuthRepository
) {

    operator fun invoke(credentials: Credentials) = repository.signIn(credentials)
}