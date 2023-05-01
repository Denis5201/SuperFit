package com.example.superfit.data

import com.example.superfit.Constants
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val token = sharedPreferences.getString(SharedPreferences.ACCESS_TOKEN)

        val request = chain.request().newBuilder().apply {
            token?.let {
                addHeader(Constants.AUTHORIZATION_HEADER, "Bearer $it")
            }
        }.build()
        return chain.proceed(request)
    }
}
