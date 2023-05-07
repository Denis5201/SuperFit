package com.example.superfit.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.superfit.domain.model.TrainingType
import com.example.superfit.domain.usecase.ClearUserDataUseCase
import com.example.superfit.domain.usecase.GetLastTwoTrainingUseCase
import com.example.superfit.domain.usecase.GetLastUserParametersUseCase
import com.example.superfit.domain.usecase.GetTrainingListUseCase
import com.example.superfit.presentation.MessageSource
import com.example.superfit.presentation.main.models.MainAction
import com.example.superfit.presentation.main.models.MainEvent
import com.example.superfit.presentation.main.models.MainUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val clearUserDataUseCase: ClearUserDataUseCase,
    private val getLastUserParametersUseCase: GetLastUserParametersUseCase,
    private val getTrainingListUseCase: GetTrainingListUseCase,
    private val getLastTwoTrainingUseCase: GetLastTwoTrainingUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<MainUiState>(MainUiState.Loading)
    val uiState: StateFlow<MainUiState> = _uiState

    private val _action = Channel<MainAction?>()
    val action = _action.receiveAsFlow()

    private var weight: String? = null
    private var height: String? = null
    private var lastExercises: Pair<TrainingType?, TrainingType?> = Pair(null, null)

    init {
        getLastUserParams()
        getLastExercises()
    }

    fun getEvent(event: MainEvent) {
        when(event) {
            MainEvent.NavigateToBody -> viewModelScope.launch {
                val weightParam = weight ?: UNDEFINED
                val heightParam = height ?: UNDEFINED
                _action.send(MainAction.NavigateToBody("/$weightParam/$heightParam"))
            }
            MainEvent.ShowAllExercises -> {
                _uiState.value = MainUiState.ShowAllExercises
            }
            MainEvent.BackToMain -> {
                _uiState.value = MainUiState.ShowMain(weight, height, lastExercises)
            }
            is MainEvent.NavigateToTraining -> viewModelScope.launch {
                _action.send(MainAction.NavigateToTraining(""))
            }
            MainEvent.ErrorShowed -> viewModelScope.launch {
                _action.send(null)
            }
            MainEvent.SignOut -> viewModelScope.launch {
                clearUserDataUseCase()
                _action.send(MainAction.SignOut)
            }
        }
    }

    private fun getLastUserParams() {
        viewModelScope.launch {
            getLastUserParametersUseCase().collect { result ->
                result.onSuccess {
                    weight = it?.weight?.toString()
                    height = it?.height?.toString()
                    _uiState.value = MainUiState.ShowMain(weight, height, lastExercises)
                }.onFailure {
                    _action.send(MainAction.ShowError(it.message ?: MessageSource.ERROR))
                }
            }
        }
    }

    private fun getLastExercises() {
        viewModelScope.launch {
            getTrainingListUseCase().collect { result ->
                result.onSuccess {
                    lastExercises = getLastTwoTrainingUseCase(it)
                    _uiState.value = MainUiState.ShowMain(weight, height, lastExercises)
                }.onFailure {
                    _action.send(MainAction.ShowError(it.message ?: MessageSource.ERROR))
                }
            }
        }
    }

    private companion object {
        const val UNDEFINED = "Undefined"
    }
}