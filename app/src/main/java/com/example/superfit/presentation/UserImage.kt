package com.example.superfit.presentation

import android.graphics.Bitmap
import java.time.LocalDate

data class UserImage(
    val id: String,
    val bitmap: Bitmap,
    val date: LocalDate
)
