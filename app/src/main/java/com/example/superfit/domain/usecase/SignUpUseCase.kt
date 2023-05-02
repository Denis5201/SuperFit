package com.example.superfit.domain.usecase

import com.example.superfit.domain.model.RegistrationForm
import com.example.superfit.domain.repository.AuthRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val repository: AuthRepository
) {

    operator fun invoke(registrationForm: RegistrationForm) = repository.signUp(registrationForm)
}