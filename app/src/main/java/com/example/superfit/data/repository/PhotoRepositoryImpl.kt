package com.example.superfit.data.repository

import android.util.Log
import com.example.superfit.data.api.PhotoApi
import com.example.superfit.domain.model.UserPhoto
import com.example.superfit.domain.repository.PhotoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(
    private val api: PhotoApi
): PhotoRepository {

    override fun getUserPhotoList(): Flow<Result<List<UserPhoto>>> = flow {
        try {
            val photoList = api.getPhotoList().map { it.toUserPhoto() }

            emit(Result.success(photoList))
        } catch (e: Exception) {
            Log.e("OPS getUserPhotoList", e.message.toString())
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)

    override fun loadNewPhoto(byteArray: ByteArray): Flow<Result<Unit>> = flow {
        try {
            val body = byteArray.toRequestBody(MEDIA_PNG.toMediaType(), 0, byteArray.size)
            val part = MultipartBody.Part.createFormData(FILE, FILENAME, body)

            api.loadPhoto(part)

            emit(Result.success(Unit))
        } catch (e: Exception) {
            Log.e("OPS loadNewPhoto", e.message.toString())
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)

    override fun getPhotoById(photoId: String): Flow<Result<ByteArray>> = flow {
        try {
            val inputStream = api.getPhoto(photoId).byteStream()

            val baos = ByteArrayOutputStream()
            inputStream.use {input ->
                baos.use {
                    input.copyTo(it)
                }
            }

            emit(Result.success(baos.toByteArray()))
        } catch (e: Exception) {
            Log.e("OPS getPhotoById", e.message.toString())
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)

    override fun deletePhoto(photoId: String): Flow<Result<Unit>> = flow {
        try {
            api.deletePhoto(photoId)

            emit(Result.success(Unit))
        } catch (e: Exception) {
            Log.e("OPS deletePhoto", e.message.toString())
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)

    private companion object {
        const val MEDIA_PNG = "image/png"
        const val FILE = "file"
        const val FILENAME = "user_photo.png"
    }

}