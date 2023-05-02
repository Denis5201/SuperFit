package com.example.superfit.presentation.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.superfit.R
import com.example.superfit.Screen
import com.example.superfit.presentation.AppProgressBar
import com.example.superfit.presentation.ErrorDialog
import com.example.superfit.presentation.auth.models.SignInAction
import com.example.superfit.presentation.auth.models.SignInEvent
import com.example.superfit.presentation.auth.models.SignInUiState
import com.example.superfit.presentation.auth.ui.LoginInput
import com.example.superfit.presentation.auth.ui.PasswordInput

@Composable
fun SignInScreen(
    navController: NavController,
    viewModel: SignInViewModel
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val action by viewModel.action.collectAsStateWithLifecycle(null)

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Image(
            bitmap = ImageBitmap.imageResource(id = R.drawable.auth_background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
        Text(
            text = stringResource(R.string.app_name),
            modifier = Modifier
                .statusBarsPadding()
                .padding(top = 44.dp),
            style = MaterialTheme.typography.titleLarge
        )
    }

    when(val state = uiState.value) {
        SignInUiState.Loading -> {
            AppProgressBar()
        }
        is SignInUiState.ShowLoginInput -> {
            LoginInput(state = state, getEvent = { viewModel.getEvent(it) } )
        }
        SignInUiState.ShowPasswordInput -> {
            PasswordInput(userName = viewModel.login, getEvent = { viewModel.getEvent(it) })
        }
    }

    if (action is SignInAction.ShowError) {
        ErrorDialog(message = (action as SignInAction.ShowError).message) {
            viewModel.getEvent(SignInEvent.ErrorShowed)
        }
    }

    LaunchedEffect(key1 = action) {
        when (action) {
            SignInAction.NavigateToSignUp -> {
                navController.navigate(Screen.SignUpScreen.route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
            SignInAction.NavigateToMain -> {
                navController.navigate(Screen.MainScreen.route) {
                    popUpTo(Screen.SignInScreen.route) {
                        inclusive = true
                    }
                }
            }
            else -> {}
        }
    }
}