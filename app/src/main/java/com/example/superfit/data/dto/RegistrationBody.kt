package com.example.superfit.data.dto

import com.example.superfit.domain.model.RegistrationForm
import kotlinx.serialization.Serializable

@Serializable
data class RegistrationBody(
    val login: String,
    val password: Int
) {
    companion object {
        fun fromRegistrationForm(registrationForm: RegistrationForm): RegistrationBody {
            return RegistrationBody(
                login = registrationForm.login,
                password = registrationForm.password
            )
        }
    }
}