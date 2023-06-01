package com.example.superfit.presentation.training

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.superfit.Constants
import com.example.superfit.domain.model.Training
import com.example.superfit.domain.model.TrainingType
import com.example.superfit.domain.usecase.CalculateTrainingPurposeUseCase
import com.example.superfit.domain.usecase.GetTrainingListUseCase
import com.example.superfit.domain.usecase.SaveTrainingResultUseCase
import com.example.superfit.presentation.MessageSource
import com.example.superfit.presentation.training.models.TrainingAction
import com.example.superfit.presentation.training.models.TrainingEvent
import com.example.superfit.presentation.training.models.TrainingUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class TrainingViewModel @Inject constructor(
    private val getTrainingListUseCase: GetTrainingListUseCase,
    private val calculateTrainingPurposeUseCase: CalculateTrainingPurposeUseCase,
    private val saveTrainingResultUseCase: SaveTrainingResultUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow<TrainingUiState>(TrainingUiState.Loading)
    val uiState: StateFlow<TrainingUiState> = _uiState

    private val _action = Channel<TrainingAction?>()
    val action = _action.receiveAsFlow()

    var type: TrainingType
        private set
    private var trainingPurpose = -1

    init {
        val typeString: String = checkNotNull(savedStateHandle[Constants.TRAINING])
        type = TrainingType.valueOf(typeString)
        getTrainingPurpose()
    }

    fun getEvent(event: TrainingEvent) {
        when (event) {
            TrainingEvent.CloseStartDialog -> {
                _uiState.value = (_uiState.value as TrainingUiState.Training).copy(
                    isDialogOpen = false
                )
            }
            is TrainingEvent.DecreaseCount -> {
                if (_uiState.value is TrainingUiState.Training) {
                    _uiState.value = (_uiState.value as TrainingUiState.Training).copy(
                        count = (_uiState.value as TrainingUiState.Training).count - event.decrease
                    )
                    if (type == TrainingType.PLANK) {
                        _uiState.value = (_uiState.value as TrainingUiState.Training).copy(
                            percent = (_uiState.value as TrainingUiState.Training)
                                .count / trainingPurpose.toFloat()
                        )
                    }
                }

            }
            TrainingEvent.SaveSuccessAndBack -> {
                //saveTraining(trainingPurpose, true)
            }
            TrainingEvent.ShowSuccess -> {
                //saveTraining(trainingPurpose)
                _uiState.value = TrainingUiState.TrainingSuccess
            }
            is TrainingEvent.ShowUnSuccess -> {
                //saveTraining(trainingPurpose - event.countRemains)
                _uiState.value = TrainingUiState.TrainingUnSuccess(type, event.countRemains)
            }
            TrainingEvent.BackToMainScreen -> viewModelScope.launch {
                _action.send(TrainingAction.NavigateBack)
            }
            TrainingEvent.ErrorShowed -> viewModelScope.launch {
                _action.send(null)
            }
        }
    }

    private fun getTrainingPurpose() {
        viewModelScope.launch {
            getTrainingListUseCase().collect { result ->
                result.onSuccess { list ->
                    val trainingList = list.filter { it.exercise == type }
                    trainingPurpose = calculateTrainingPurposeUseCase(trainingList, type)

                    _uiState.value = TrainingUiState.Training(type, trainingPurpose)
                }.onFailure {
                    _action.send(TrainingAction.ShowError(it.message ?: MessageSource.ERROR))
                }
            }
        }
    }

    private fun saveTraining(countDone: Int, goBack: Boolean = false) {
        if (countDone <= 0) {
            return
        }
        viewModelScope.launch {
            val savingTraining = Training(LocalDate.now(), type, countDone)
            saveTrainingResultUseCase(savingTraining).collect { result ->
                result.onSuccess {
                    if (goBack) {
                        _action.send(TrainingAction.NavigateBack)
                    }
                }.onFailure {
                    _action.send(TrainingAction.ShowError(it.message ?: MessageSource.ERROR))
                }
            }
        }
    }
}