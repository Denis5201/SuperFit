package com.example.superfit.presentation.graphics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.superfit.presentation.graphics.models.StatisticsAction
import com.example.superfit.presentation.graphics.models.StatisticsEvent
import com.example.superfit.presentation.graphics.models.StatisticsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(

) : ViewModel() {

    private val _uiState = MutableStateFlow<StatisticsUiState>(StatisticsUiState.Loading)
    val uiState: StateFlow<StatisticsUiState> = _uiState

    private val _action = Channel<StatisticsAction?>()
    val action = _action.receiveAsFlow()

    fun getEvent(event: StatisticsEvent) {
        when (event) {
            StatisticsEvent.BackToMyBody -> viewModelScope.launch {
                _action.send(StatisticsAction.BackToMyBody)
            }
            StatisticsEvent.ErrorShowed -> viewModelScope.launch {
                _action.send(null)
            }
        }
    }
}