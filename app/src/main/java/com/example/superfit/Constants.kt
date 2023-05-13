package com.example.superfit

import java.time.format.DateTimeFormatter

object Constants {
    const val BASE_URL = "http://fitness.wsmob.xyz:22169/"
    const val AUTHORIZATION_HEADER = "Authorization"
    const val WEIGHT = "weight"
    const val HEIGHT = "height"
    const val TRAINING = "training"
    val DATE_FORMAT: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    val MONTH_STRING_IDS: List<Int> = listOf(
        R.string.january,
        R.string.february,
        R.string.march,
        R.string.april,
        R.string.may,
        R.string.june,
        R.string.july,
        R.string.august,
        R.string.september,
        R.string.october,
        R.string.november,
        R.string.december
    )
}