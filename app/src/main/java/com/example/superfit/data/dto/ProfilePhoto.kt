package com.example.superfit.data.dto

import com.example.superfit.domain.model.UserPhoto
import kotlinx.serialization.Serializable
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

@Serializable
data class ProfilePhoto(
    val id: String,
    val uploaded: Long
) {
    fun toUserPhoto(): UserPhoto {
        return UserPhoto(
            id = id,
            uploaded = LocalDateTime.ofInstant(Instant.ofEpochSecond(uploaded), ZoneOffset.UTC).toLocalDate()
        )
    }
}
