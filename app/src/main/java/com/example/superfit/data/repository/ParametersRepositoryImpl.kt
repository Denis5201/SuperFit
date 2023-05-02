package com.example.superfit.data.repository

import android.util.Log
import com.example.superfit.data.api.ParamsApi
import com.example.superfit.data.dto.BodyParameters
import com.example.superfit.domain.model.UserParameters
import com.example.superfit.domain.repository.ParametersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ParametersRepositoryImpl @Inject constructor(
    private val api: ParamsApi
) : ParametersRepository {

    override fun setNewUserParams(userParameters: UserParameters): Flow<Result<Unit>> = flow {
        try {
            api.setParams(BodyParameters.fromUserParameters(userParameters))

            emit(Result.success(Unit))
        } catch (e: Exception) {
            Log.e("OPS setNewUserParams", e.message.toString())
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)

    override fun getLastUserParams(): Flow<Result<UserParameters?>> = flow {
        try {
            val userParameters = api.paramsHistory().lastOrNull()?.toUserParameters()

            emit(Result.success(userParameters))
        } catch (e: Exception) {
            Log.e("OPS getLastUserParams", e.message.toString())
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)

    override fun getUserParamsHistory(): Flow<Result<List<UserParameters>>> = flow {
        try {
             val userParamsList = api.paramsHistory().map { it.toUserParameters() }

            emit(Result.success(userParamsList))
        } catch (e: Exception) {
            Log.e("OPS getUserParamsHistory", e.message.toString())
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)

    override fun getProfile(): Flow<Result<String>> = flow {
        try {
            val profile = api.getProfile()

            emit(Result.success(profile.login))
        } catch (e: Exception) {
            Log.e("OPS getProfile", e.message.toString())
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)

}