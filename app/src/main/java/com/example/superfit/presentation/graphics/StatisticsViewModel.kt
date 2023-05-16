package com.example.superfit.presentation.graphics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.superfit.domain.model.Training
import com.example.superfit.domain.model.TrainingType
import com.example.superfit.presentation.graphics.models.StatisticsAction
import com.example.superfit.presentation.graphics.models.StatisticsEvent
import com.example.superfit.presentation.graphics.models.StatisticsUiState
import com.example.superfit.presentation.graphics.models.WeightParam
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(

) : ViewModel() {

    private val _uiState = MutableStateFlow<StatisticsUiState>(StatisticsUiState.Loading)
    val uiState: StateFlow<StatisticsUiState> = _uiState

    private val _action = Channel<StatisticsAction?>()
    val action = _action.receiveAsFlow()

    private val weightList: List<WeightParam> = listOf(WeightParam(50, LocalDate.now()),
        WeightParam(55, LocalDate.now()), WeightParam(57, LocalDate.now()),
        WeightParam(55, LocalDate.now()), WeightParam(60, LocalDate.now()),
        WeightParam(62, LocalDate.now()), WeightParam(65, LocalDate.now()),
        WeightParam(68, LocalDate.now()), WeightParam(69, LocalDate.now()), WeightParam(70, LocalDate.now()))
    private val allTraining: List<Training> = listOf(Training(LocalDate.now(), TrainingType.PUSH_UP, 10),
        Training(LocalDate.now(), TrainingType.PUSH_UP, 15), Training(LocalDate.now(), TrainingType.PUSH_UP, 18),
        Training(LocalDate.now(), TrainingType.PUSH_UP, 20), Training(LocalDate.now(), TrainingType.PUSH_UP, 20),
        Training(LocalDate.now(), TrainingType.PUSH_UP, 22), Training(LocalDate.now(), TrainingType.PUSH_UP, 25))

    init {
        viewModelScope.launch {
            delay(1000)
            _uiState.value = StatisticsUiState.ShowCharts(weightList, TrainingType.PUSH_UP, allTraining)
        }
    }

    fun getEvent(event: StatisticsEvent) {
        when (event) {
            StatisticsEvent.BackToMyBody -> viewModelScope.launch {
                _action.send(StatisticsAction.BackToMyBody)
            }
            is StatisticsEvent.SelectTrainingType -> {
                _uiState.value = StatisticsUiState.ShowCharts(
                    weightData = weightList,
                    selectedTrainingType = event.trainingType,
                    trainingList = allTraining.filter { it.exercise == event.trainingType }
                )
            }
            StatisticsEvent.ErrorShowed -> viewModelScope.launch {
                _action.send(null)
            }
        }
    }
}