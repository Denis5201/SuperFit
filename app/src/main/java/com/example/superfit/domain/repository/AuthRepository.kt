package com.example.superfit.domain.repository

import com.example.superfit.domain.model.Credentials
import com.example.superfit.domain.model.RegistrationForm
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun signIn(credentials: Credentials): Flow<Result<Unit>>

    fun signUp(registrationForm: RegistrationForm): Flow<Result<Unit>>
}