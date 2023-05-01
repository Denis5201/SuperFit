package com.example.superfit.presentation.auth

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.superfit.Screen
import com.example.superfit.presentation.auth.models.SignInAction
import com.example.superfit.presentation.auth.models.SignInEvent
import com.example.superfit.presentation.auth.models.SignInUiState

@Composable
fun SignInScreen(navController: NavController) {
    val viewModel: SignInViewModel = viewModel()

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val action by viewModel.action.collectAsStateWithLifecycle(null)

    when(uiState) {
        SignInUiState.Loading -> {
            Button(onClick = { viewModel.getEvent(SignInEvent.AlreadyLoad) }) {
                Text(text = "Already loading")
            }
        }
        SignInUiState.ShowLoginInput -> {
            Button(onClick = { viewModel.getEvent(SignInEvent.Navigate) }) {
                Text(text = "Navigate")
            }
        }
        SignInUiState.ShowPasswordInput -> {

        }
    }
    LaunchedEffect(key1 = action) {
        when (action) {
            SignInAction.Navigate -> {
                navController.navigate(Screen.SignUpScreen.route)
            }
            else -> {}
        }
    }
}