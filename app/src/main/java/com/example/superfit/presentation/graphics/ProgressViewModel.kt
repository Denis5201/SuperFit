package com.example.superfit.presentation.graphics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

) : ViewModel() {

    private val _uiState = MutableStateFlow<ProgressUiState>(ProgressUiState.Loading)
    val uiState: StateFlow<ProgressUiState> = _uiState

    private val _action = Channel<ProgressAction?>()
    val action = _action.receiveAsFlow()

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
}