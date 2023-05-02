package com.example.superfit.domain.usecase

import com.example.superfit.domain.repository.LocalSettings
import javax.inject.Inject

class GetUserLoginUseCase @Inject constructor(
    private val localSettings: LocalSettings
) {

    operator fun invoke(): String? = localSettings.getUserLogin()
}