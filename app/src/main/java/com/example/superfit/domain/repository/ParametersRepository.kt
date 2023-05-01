package com.example.superfit.domain.repository

import com.example.superfit.domain.model.UserParameters
import kotlinx.coroutines.flow.Flow

interface ParametersRepository {

    fun setNewUserParams(userParameters: UserParameters): Flow<Result<Unit>>

    fun getLastUserParams(): Flow<Result<UserParameters?>>

    fun getUserParamsHistory(): Flow<Result<List<UserParameters>>>
}