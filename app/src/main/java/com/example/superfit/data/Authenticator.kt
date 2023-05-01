package com.example.superfit.data

import com.example.superfit.Constants
import com.example.superfit.data.api.AuthApi
import com.example.superfit.data.dto.AccessToken
import com.example.superfit.data.dto.AuthCredential
import com.example.superfit.data.dto.RefreshToken
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class Authenticator @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val refreshApi: AuthApi
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {

        if (response.responseCount > 1) {
            return null
        }

        val refreshToken = sharedPreferences.getString(SharedPreferences.REFRESH_TOKEN)

        if (refreshToken.isNullOrEmpty()) {
            return null
        }

        var newAccessTokensResult = getNewAccessTokensResult(refreshToken)

        if (newAccessTokensResult.isFailure) {
            val login = sharedPreferences.getString(SharedPreferences.USER_EMAIL)
            val password = sharedPreferences.getString(SharedPreferences.USER_PASSWORD)

            if (login.isNullOrEmpty() || password.isNullOrEmpty()) {
                return null
            }
            val newRefreshTokenResult = runBlocking {
                try {
                    val token = refreshApi.login(AuthCredential(login, password))
                    Result.success(token)
                } catch (e: Exception) {
                    Result.failure(e)
                }
            }

            if (newRefreshTokenResult.isFailure) {
                return null
            }
            newRefreshTokenResult.onSuccess {
                sharedPreferences.setString(SharedPreferences.REFRESH_TOKEN, it.refreshToken)
                newAccessTokensResult = getNewAccessTokensResult(it.refreshToken)
            }
        }

        newAccessTokensResult.onSuccess {
            sharedPreferences.setString(SharedPreferences.ACCESS_TOKEN, it.accessToken)
        }

        return if (newAccessTokensResult.isFailure) {
            response.request.newBuilder()
                .header(
                    Constants.AUTHORIZATION_HEADER,
                    "Bearer ${sharedPreferences.getString(SharedPreferences.ACCESS_TOKEN)}"
                )
                .build()
        } else {
            response.request.newBuilder()
                .header(
                    Constants.AUTHORIZATION_HEADER,
                    "Bearer ${newAccessTokensResult.getOrThrow().accessToken}"
                )
                .build()
        }
    }

    private val Response.responseCount: Int
        get() = generateSequence(this) { it.priorResponse }.count()

    private fun getNewAccessTokensResult(refreshToken: String): Result<AccessToken> {
        return runBlocking {
            try {
                val token = refreshApi.getAccessToken(RefreshToken(refreshToken))
                Result.success(token)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}