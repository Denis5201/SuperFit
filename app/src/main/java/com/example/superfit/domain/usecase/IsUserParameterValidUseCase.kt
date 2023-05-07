package com.example.superfit.domain.usecase

import javax.inject.Inject

class IsUserParameterValidUseCase @Inject constructor() {

    operator fun invoke(value: String): Boolean {
        if (value.length !in 2..3) {
            return false
        }
        if (value.toInt() !in 10..300) {
            return false
        }
        return true
    }
}