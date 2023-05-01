package com.example.superfit.domain.model

import java.time.LocalDate

data class Training(
    val date: LocalDate,
    val exercise: TrainingType,
    val repeatCount: Int
)
