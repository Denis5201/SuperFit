package com.example.superfit.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.superfit.domain.model.RegistrationForm
import com.example.superfit.domain.usecase.IsEmailFormatUseCase
import com.example.superfit.domain.usecase.IsStringsEmptyUseCase
import com.example.superfit.domain.usecase.SignUpUseCase
import com.example.superfit.presentation.MessageSource
import com.example.superfit.presentation.auth.models.SignUpAction
import com.example.superfit.presentation.auth.models.SignUpEvent
import com.example.superfit.presentation.auth.models.SignUpUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val isStringsEmptyUseCase: IsStringsEmptyUseCase,
    private val isEmailFormatUseCase: IsEmailFormatUseCase,
    private val messageSource: MessageSource
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState> = _uiState

    private val _action = Channel<SignUpAction?>()
    val action = _action.receiveAsFlow()

    fun getEvent(event: SignUpEvent) {
        when(event) {
            is SignUpEvent.InputUserName -> _uiState.value = _uiState.value.copy(userName = event.text)
            is SignUpEvent.InputEmail -> _uiState.value = _uiState.value.copy(email = event.text)
            is SignUpEvent.InputCode -> {
                if (isValidInputCode(event.text)) {
                    _uiState.value = _uiState.value.copy(code = event.text)
                    return
                }
            }
            is SignUpEvent.InputRepeatCode -> {
                if (isValidInputCode(event.text)) {
                    _uiState.value = _uiState.value.copy(repeatCode = event.text)
                    return
                }
            }
            is SignUpEvent.SignUp -> register()
            is SignUpEvent.NavigateToSignIn -> viewModelScope.launch {
                _action.send(SignUpAction.NavigateToSignIn)
            }
            is SignUpEvent.ErrorShowed -> viewModelScope.launch {
                _action.send(null)
            }
        }
    }

    private fun isValidInputCode(code: String): Boolean {
        if (code.length > 4) {
            showError(MessageSource.WRONG_LENGTH_CODE)
            return false
        }
        if (code.isNotEmpty() && code.last() == '0') {
            showError(MessageSource.CODE_CONTAIN_ZERO)
            return false
        }
        return true
    }

    private fun isAllInputValid(): Boolean {
        if (isStringsEmptyUseCase(
                _uiState.value.userName.trim(),
                _uiState.value.email.trim(),
                _uiState.value.code.trim(),
                _uiState.value.repeatCode.trim())
        ) {
            showError(MessageSource.EMPTY_INPUT)
            return false
        }
        if (!isEmailFormatUseCase(_uiState.value.email.trim())) {
            showError(MessageSource.WRONG_EMAIL_FORMAT)
            return false
        }
        if (_uiState.value.code.length != 4) {
            showError(MessageSource.WRONG_LENGTH_CODE)
            return false
        }
        if (_uiState.value.code != _uiState.value.repeatCode) {
            showError(MessageSource.CODE_NOT_EQUAL_WITH_REPEAT)
            return false
        }

        return true
    }

    private fun register() {
        if (!isAllInputValid()) {
            return
        }
        viewModelScope.launch {
            signUpUseCase(RegistrationForm(
                    login = _uiState.value.email,
                    password = _uiState.value.code.toInt())
            ).collect { result ->
                result.onSuccess {
                    _action.send(SignUpAction.NavigateToSignIn)
                }.onFailure {
                    _action.send(SignUpAction.ShowError(it.message ?: MessageSource.ERROR))
                }
            }
        }
    }

    private fun showError(reason: Int) {
        viewModelScope.launch {
            _action.send(
                SignUpAction.ShowError(messageSource.getMessage(reason))
            )
        }
    }
}