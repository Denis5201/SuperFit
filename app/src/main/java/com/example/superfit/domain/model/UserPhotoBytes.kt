package com.example.superfit.domain.model

import java.time.LocalDate

data class UserPhotoBytes(
    val id: String,
    val image: ByteArray,
    val date: LocalDate
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserPhotoBytes

        if (id != other.id) return false
        if (!image.contentEquals(other.image)) return false
        if (date != other.date) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + image.contentHashCode()
        result = 31 * result + date.hashCode()
        return result
    }
}
