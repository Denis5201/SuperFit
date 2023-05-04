package com.example.superfit.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.superfit.domain.model.Credentials
import com.example.superfit.domain.usecase.GetUserLoginUseCase
import com.example.superfit.domain.usecase.IsEmailFormatUseCase
import com.example.superfit.domain.usecase.IsFirstRunAppUseCase
import com.example.superfit.domain.usecase.IsStringsEmptyUseCase
import com.example.superfit.domain.usecase.IsTokenValidYetUseCase
import com.example.superfit.domain.usecase.SignInUseCase
import com.example.superfit.presentation.MessageSource
import com.example.superfit.presentation.auth.models.SignInAction
import com.example.superfit.presentation.auth.models.SignInEvent
import com.example.superfit.presentation.auth.models.SignInUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val isStringsEmptyUseCase: IsStringsEmptyUseCase,
    private val isEmailFormatUseCase: IsEmailFormatUseCase,
    private val isTokenValidYetUseCase: IsTokenValidYetUseCase,
    private val getUserLoginUseCase: GetUserLoginUseCase,
    private val messageSource: MessageSource,
    isFirstRunAppUseCase: IsFirstRunAppUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<SignInUiState>(SignInUiState.Loading)
    val uiState: StateFlow<SignInUiState> = _uiState

    private val _action = Channel<SignInAction?>()
    val action = _action.receiveAsFlow()

    var login: String = ""
        private set
    private var password: String = ""

    init {
        if (isFirstRunAppUseCase()) {
            viewModelScope.launch {
                _action.send(SignInAction.NavigateToSignUp)
            }
        } else {
            isTokenValid()
        }
    }

    fun getEvent(event: SignInEvent) {
        when(event) {
            is SignInEvent.InputLogin -> {
                _uiState.value = SignInUiState.ShowLoginInput(event.text)
            }
            is SignInEvent.GoInputPassword -> {
                if (isEmailValid(event.login)) {
                    login = event.login
                    _uiState.value = SignInUiState.ShowPasswordInput
                }
            }
            is SignInEvent.InputPassword -> {
                password += event.newDigit
                if (password.length >= 4) {
                    signIn()
                    _uiState.value = SignInUiState.Loading
                    return
                }
                _uiState.value = SignInUiState.ShowPasswordInput
            }
            is SignInEvent.BackToInputLogin -> {
                password = ""
                _uiState.value = SignInUiState.ShowLoginInput(login)
            }
            is SignInEvent.NavigateToSignUp -> viewModelScope.launch {
                _action.send(SignInAction.NavigateToSignUp)
            }
            is SignInEvent.ErrorShowed -> viewModelScope.launch {
                _action.send(null)
            }
        }
    }

    private fun isEmailValid(email: String): Boolean {
        if (isStringsEmptyUseCase(email.trim())) {
            viewModelScope.launch {
                _action.send(SignInAction.ShowError(
                    messageSource.getMessage(MessageSource.EMPTY_INPUT)
                ))
            }
            return false
        }
        if (!isEmailFormatUseCase(email)) {
            viewModelScope.launch {
                _action.send(SignInAction.ShowError(
                    messageSource.getMessage(MessageSource.WRONG_EMAIL_FORMAT)
                ))
            }
            return false
        }
        return true
    }

    private fun signIn() {
        viewModelScope.launch {
            signInUseCase(Credentials(login = login, password = password)).collect { result ->
                result.onSuccess {
                    _action.send(SignInAction.NavigateToMain)
                }.onFailure {
                    password = ""
                    _uiState.value = SignInUiState.ShowPasswordInput
                    _action.send(SignInAction.ShowError(it.message ?: MessageSource.ERROR))
                }
            }
        }
    }

    private fun isTokenValid() {
        viewModelScope.launch {
            if (isTokenValidYetUseCase()) {
                _action.send(SignInAction.NavigateToMain)
            } else {
                _uiState.value = SignInUiState.ShowLoginInput(getUserLoginUseCase() ?: "")
            }
        }
    }
}