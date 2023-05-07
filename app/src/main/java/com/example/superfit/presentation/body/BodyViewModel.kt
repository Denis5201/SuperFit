package com.example.superfit.presentation.body

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.superfit.Constants
import com.example.superfit.domain.model.UserParameters
import com.example.superfit.domain.usecase.IsStringsEmptyUseCase
import com.example.superfit.domain.usecase.IsUserParameterValidUseCase
import com.example.superfit.domain.usecase.SaveNewUserParamsUseCase
import com.example.superfit.presentation.MessageSource
import com.example.superfit.presentation.body.models.BodyAction
import com.example.superfit.presentation.body.models.BodyEvent
import com.example.superfit.presentation.body.models.BodyUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class BodyViewModel @Inject constructor(
    private val isUserParameterValidUseCase: IsUserParameterValidUseCase,
    private val isStringsEmptyUseCase: IsStringsEmptyUseCase,
    private val saveNewUserParamsUseCase: SaveNewUserParamsUseCase,
    private val messageSource: MessageSource,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(BodyUiState())
    val uiState: StateFlow<BodyUiState> = _uiState

    private val _action = Channel<BodyAction?>()
    val action = _action.receiveAsFlow()

    private var weight: String
    private var height: String

    init {
        weight = checkNotNull(savedStateHandle[Constants.WEIGHT])
        height = checkNotNull(savedStateHandle[Constants.HEIGHT])

        if (weight.length > 3) weight = UNDEFINED
        if (height.length > 3) height = UNDEFINED

        _uiState.value = _uiState.value.copy(isLoading = false, weight = weight, height = height)
    }

    fun getEvent(event: BodyEvent) {
        when (event) {
            is BodyEvent.NewWeightValue -> {
                if (event.save) {
                    saveNewUserParams(WEIGHT_DIALOG, event.value)
                    _uiState.value = _uiState.value.copy(
                        changingWeight = false,
                        currentChangingText = ""
                    )
                } else {
                    _uiState.value = _uiState.value.copy(currentChangingText = event.value)
                }
            }
            is BodyEvent.NewHeightValue -> {
                if (event.save) {
                    saveNewUserParams(HEIGHT_DIALOG, event.value)
                    _uiState.value = _uiState.value.copy(
                        changingHeight = false,
                        currentChangingText = ""
                    )
                } else {
                    _uiState.value = _uiState.value.copy(currentChangingText = event.value)
                }
            }
            BodyEvent.OpenPhotoDialog -> {
                _uiState.value = _uiState.value.copy(choosingImage = true)
            }
            BodyEvent.ClosePhotoDialog -> {
                _uiState.value = _uiState.value.copy(choosingImage = false)
            }
            is BodyEvent.NewPhoto -> {}
            is BodyEvent.OpenChangingParamsDialog -> {
                if (event.whose == WEIGHT_DIALOG) {
                    _uiState.value = _uiState.value.copy(changingWeight = true)
                }
                if (event.whose == HEIGHT_DIALOG) {
                    _uiState.value = _uiState.value.copy(changingHeight = true)
                }
            }
            BodyEvent.CloseChangingParamsDialog -> {
                _uiState.value = _uiState.value.copy(changingWeight = false, changingHeight = false)
            }
            BodyEvent.NavigateToImages -> viewModelScope.launch {
                _action.send(BodyAction.NavigateToImages)
            }
            BodyEvent.NavigateToProgress -> viewModelScope.launch {
                _action.send(BodyAction.NavigateToProgress)
            }
            BodyEvent.NavigateToStatistics -> viewModelScope.launch {
                _action.send(BodyAction.NavigateToStatistics)
            }
            BodyEvent.ErrorShowed -> viewModelScope.launch {
                _action.send(null)
            }
        }
    }

    private fun saveNewUserParams(whose: Int, value: String) {
        if (isStringsEmptyUseCase(value)) {
            viewModelScope.launch {
                _action.send(
                    BodyAction.ShowError(messageSource.getMessage(MessageSource.EMPTY_INPUT))
                )
            }
            return
        }
        if (!isUserParameterValidUseCase(value)) {
            viewModelScope.launch {
                _action.send(
                    BodyAction.ShowError(messageSource.getMessage(MessageSource.WRONG_PARAM_VALUE))
                )
            }
            return
        }

        val newUserParams = if (whose == WEIGHT_DIALOG) {
            weight = value
            if (height.length > 3) {
                _uiState.value = _uiState.value.copy(changingHeight = true)
                return
            }
            UserParameters(
                weight = value.toInt(),
                height = height.toInt(),
                date = LocalDate.now()
            )
        } else {
            height = value
            if (weight.length > 3) {
                _uiState.value = _uiState.value.copy(changingWeight = true)
                return
            }
            UserParameters(
                weight = weight.toInt(),
                height = value.toInt(),
                date = LocalDate.now()
            )
        }
        viewModelScope.launch {
            saveNewUserParamsUseCase(newUserParams).collect { result ->
                result.onSuccess {
                    _uiState.value = _uiState.value.copy(weight = weight, height = height)
                }.onFailure {
                    _action.send(BodyAction.ShowError(it.message ?: MessageSource.ERROR))
                }
            }
        }
    }

    companion object {
        const val WEIGHT_DIALOG = 0
        const val HEIGHT_DIALOG = 1
        const val UNDEFINED = "Undefined"
    }
}