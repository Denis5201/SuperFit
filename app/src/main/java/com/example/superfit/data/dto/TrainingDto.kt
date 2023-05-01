package com.example.superfit.data.dto

import com.example.superfit.domain.model.Training
import com.example.superfit.domain.model.TrainingType
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class TrainingDto(
    val date: String,
    val exercise: String,
    val repeatCount: Int
) {
    fun toTraining(): Training {
        return Training(
            date = LocalDate.parse(date),
            exercise = TrainingType.valueOf(exercise),
            repeatCount = repeatCount
        )
    }
    companion object {
        fun fromTraining(training: Training): TrainingDto {
            return TrainingDto(
                date = training.date.toString(),
                exercise = training.exercise.name,
                repeatCount = training.repeatCount
            )
        }
    }
}
