package com.example.superfit.data.dto

import com.example.superfit.domain.model.UserParameters
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class BodyParameters(
    val weight: Int,
    val height: Int,
    val date: String
) {
    fun toUserParameters(): UserParameters {
        return UserParameters(
            weight = weight,
            height = height,
            date = LocalDate.parse(date)
        )
    }

    companion object {
        fun fromUserParameters(userParameters: UserParameters): BodyParameters {
            return BodyParameters(
                weight = userParameters.weight,
                height = userParameters.height,
                date = userParameters.date.toString()
            )
        }
    }
}
