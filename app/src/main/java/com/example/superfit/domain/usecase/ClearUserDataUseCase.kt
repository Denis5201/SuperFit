package com.example.superfit.domain.usecase

import com.example.superfit.domain.repository.LocalSettings
import javax.inject.Inject

class ClearUserDataUseCase @Inject constructor(
    private val localSettings: LocalSettings
) {

    operator fun invoke() = localSettings.clearUserInfo()
}