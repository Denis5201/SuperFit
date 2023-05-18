package com.example.superfit.presentation.graphics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.superfit.domain.model.TrainingType
import com.example.superfit.domain.usecase.CalculateProgressUseCase
import com.example.superfit.domain.usecase.GetTrainingListUseCase
import com.example.superfit.presentation.MessageSource
import com.example.superfit.presentation.graphics.models.ProgressAction
import com.example.superfit.presentation.graphics.models.ProgressEvent
import com.example.superfit.presentation.graphics.models.ProgressUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProgressViewModel @Inject constructor(
    private val getTrainingListUseCase: GetTrainingListUseCase,
    private val calculateProgressUseCase: CalculateProgressUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProgressUiState>(ProgressUiState.Loading)
    val uiState: StateFlow<ProgressUiState> = _uiState

    private val _action = Channel<ProgressAction?>()
    val action = _action.receiveAsFlow()

    init {
        getTraining()
    }

    fun getEvent(event: ProgressEvent) {
        when (event) {
            ProgressEvent.BackToMyBody -> viewModelScope.launch {
                _action.send(ProgressAction.BackToMyBody)
            }
            ProgressEvent.ErrorShowed -> viewModelScope.launch {
                _action.send(null)
            }
        }
    }

    private fun getTraining() {
        viewModelScope.launch {
            getTrainingListUseCase().collect { result ->
                result.onSuccess {
                    _uiState.value = ProgressUiState.ShowProgress(
                        pushUps = calculateProgressUseCase(it, TrainingType.PUSH_UP),
                        plank = calculateProgressUseCase(it, TrainingType.PLANK),
                        crunch = calculateProgressUseCase(it, TrainingType.CRUNCH),
                        squats = calculateProgressUseCase(it, TrainingType.SQUATS),
                        running = calculateProgressUseCase(it, TrainingType.RUNNING)
                    )
                }.onFailure {
                    _action.send(ProgressAction.ShowError(it.message ?: MessageSource.ERROR))
                    _uiState.value = ProgressUiState.ShowProgress()
                }
            }
        }
    }
}