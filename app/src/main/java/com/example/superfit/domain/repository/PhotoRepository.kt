package com.example.superfit.domain.repository

import com.example.superfit.domain.model.UserPhoto
import kotlinx.coroutines.flow.Flow

interface PhotoRepository {

    fun getUserPhotoList(): Flow<Result<List<UserPhoto>>>

    fun loadNewPhoto(byteArray: ByteArray): Flow<Result<Unit>>

    fun getPhotoById(photoId: String): Flow<Result<ByteArray>>

    fun deletePhoto(photoId: String): Flow<Result<Unit>>
}