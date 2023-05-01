package com.example.superfit.data.repository

import android.util.Log
import com.example.superfit.data.SharedPreferences
import com.example.superfit.data.api.AuthApi
import com.example.superfit.data.dto.AuthCredential
import com.example.superfit.data.dto.RegistrationBody
import com.example.superfit.domain.model.Credentials
import com.example.superfit.domain.model.RegistrationForm
import com.example.superfit.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApi,
    private val sharedPreferences: SharedPreferences
) : AuthRepository {

    override fun signIn(credentials: Credentials): Flow<Result<Unit>> = flow {
        try {
            val refreshToken = api.login(AuthCredential.fromCredentials(credentials))

            sharedPreferences.setString(SharedPreferences.REFRESH_TOKEN, refreshToken.refreshToken)
            sharedPreferences.setBoolean(SharedPreferences.FIRST_RUN, false)

            emit(Result.success(Unit))
        } catch (e: Exception) {
            Log.e("OPS signIn", e.message.toString())
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)

    override fun signUp(registrationForm: RegistrationForm): Flow<Result<Unit>> = flow {
        try {
            api.registration(RegistrationBody.fromRegistrationForm(registrationForm))

            sharedPreferences.setBoolean(SharedPreferences.FIRST_RUN, false)

            emit(Result.success(Unit))
        } catch (e: Exception) {
            Log.e("OPS signUp", e.message.toString())
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)

}