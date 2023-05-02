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
import com.example.superfit.presentation.ErrorDialog
import com.example.superfit.presentation.auth.models.SignUpAction
import com.example.superfit.presentation.auth.models.SignUpEvent
import com.example.superfit.presentation.auth.ui.RegistrationInput

@Composable
fun SignUpScreen(
    navController: NavController,
    viewModel: SignUpViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
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

    RegistrationInput(state = uiState, getEvent = { viewModel.getEvent(it) })

    if (action is SignUpAction.ShowError) {
        ErrorDialog(message = (action as SignUpAction.ShowError).message) {
            viewModel.getEvent(SignUpEvent.ErrorShowed)
        }
    }

    LaunchedEffect(key1 = action) {
        when (action) {
            SignUpAction.NavigateToSignIn -> {
                navController.navigate(Screen.SignInScreen.route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
            else -> {}
        }
    }
}