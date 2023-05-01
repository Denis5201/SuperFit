package com.example.superfit.domain.model

import java.time.LocalDate

data class UserParameters(
    val weight: Int,
    val height: Int,
    val date: LocalDate
)
