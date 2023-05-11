package com.example.superfit.presentation.images

import android.graphics.BitmapFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.superfit.domain.usecase.DeletePhotoUseCase
import com.example.superfit.domain.usecase.GetAllUserPhotoUseCase
import com.example.superfit.domain.usecase.SplitUserPhotoBytesByDateUseCase
import com.example.superfit.presentation.MessageSource
import com.example.superfit.presentation.UserImage
import com.example.superfit.presentation.images.models.ImagesAction
import com.example.superfit.presentation.images.models.ImagesEvent
import com.example.superfit.presentation.images.models.ImagesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImagesViewModel @Inject constructor(
    private val getAllUserPhotoUseCase: GetAllUserPhotoUseCase,
    private val deletePhotoUseCase: DeletePhotoUseCase,
    private val splitUserPhotoBytesByDateUseCase: SplitUserPhotoBytesByDateUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<ImagesUiState>(ImagesUiState.Loading)
    val uiState: StateFlow<ImagesUiState> = _uiState

    private val _action = Channel<ImagesAction?>()
    val action = _action.receiveAsFlow()

    private var userPhoto: MutableList<MutableList<UserImage>> = mutableListOf()

    init {
        getImageList()
    }

    fun getEvent(event: ImagesEvent) {
        when (event) {
            is ImagesEvent.ShowImage -> {
                _uiState.value = ImagesUiState.ShowImage(event.image)
            }
            ImagesEvent.NavigateBack -> viewModelScope.launch {
                _action.send(ImagesAction.NavigateBack)
            }
            ImagesEvent.BackToImageList -> {
                _uiState.value = ImagesUiState.ShowImageList(userPhoto)
            }
            is ImagesEvent.OpenDeleteDialog -> {
                _uiState.value = ImagesUiState.ShowImageList(
                    userPhoto, true, Pair(event.galleryIndex, event.itemIndex)
                )
            }
            ImagesEvent.CloseDeleteDialog -> {
                _uiState.value = ImagesUiState.ShowImageList(
                    userPhoto, false, Pair(null, null)
                )
            }
            is ImagesEvent.DeletePhoto -> {
                deletePhoto(event.galleryIndex, event.itemIndex)
            }
            ImagesEvent.ErrorShowed -> viewModelScope.launch {
                _action.send(null)
            }
        }
    }

    private fun getImageList() {
        viewModelScope.launch {
            getAllUserPhotoUseCase().collect { result ->
                result.onSuccess { list ->

                    val sections = splitUserPhotoBytesByDateUseCase(list)
                    if (sections.isNotEmpty()) {
                        sections.forEach {
                            userPhoto.add(
                                it.map { photo ->
                                    UserImage(
                                        id = photo.id,
                                        bitmap = BitmapFactory.decodeByteArray(
                                            photo.image, 0, photo.image.size
                                        ),
                                        date = photo.date
                                    )
                                }.toMutableList()
                            )
                        }
                    }
                    _uiState.value = ImagesUiState.ShowImageList(userPhoto)
                }.onFailure {
                    _action.send(ImagesAction.ShowError(it.message ?: MessageSource.ERROR))
                    _uiState.value = ImagesUiState.ShowImageList(userPhoto)
                }
            }
        }
    }

    private fun deletePhoto(galleryIndex: Int, itemIndex: Int) {
        viewModelScope.launch {
            val photoId = userPhoto[galleryIndex][itemIndex].id
            deletePhotoUseCase(photoId).collect { result ->
                result.onSuccess {
                    userPhoto[galleryIndex].removeAt(itemIndex)
                    _uiState.value = ImagesUiState.ShowImageList(userPhoto)
                }.onFailure {
                    _action.send(ImagesAction.ShowError(it.message ?: MessageSource.ERROR))
                }
            }
        }
    }
}