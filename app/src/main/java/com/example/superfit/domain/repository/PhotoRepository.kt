package com.example.superfit.domain.repository

import com.example.superfit.domain.model.UserPhoto
import com.example.superfit.domain.model.UserPhotoBytes
import kotlinx.coroutines.flow.Flow

interface PhotoRepository {

    fun getUserPhotoList(): Flow<Result<List<UserPhoto>>>

    fun loadNewPhoto(byteArray: ByteArray): Flow<Result<Unit>>

    fun getPhotoById(photoId: String): Flow<Result<ByteArray>>

    fun deletePhoto(photoId: String): Flow<Result<Unit>>

    fun getAllPhoto(): Flow<Result<List<UserPhotoBytes>>>
}