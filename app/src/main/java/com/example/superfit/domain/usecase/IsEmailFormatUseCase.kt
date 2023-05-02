package com.example.superfit.domain.usecase

import android.util.Patterns
import javax.inject.Inject

class IsEmailFormatUseCase @Inject constructor() {

    operator fun invoke(email: String): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()
}
