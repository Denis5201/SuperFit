package com.example.superfit.presentation.body

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.superfit.Screen
import com.example.superfit.presentation.AppProgressBar
import com.example.superfit.presentation.ErrorDialog
import com.example.superfit.presentation.body.models.BodyAction
import com.example.superfit.presentation.body.models.BodyEvent
import com.example.superfit.presentation.body.ui.UserBody

@Composable
fun BodyScreen(navController: NavController, viewModel: BodyViewModel) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val action by viewModel.action.collectAsStateWithLifecycle(null)

    if (uiState.isLoading) {
        AppProgressBar()
    } else {
        UserBody(uiState = uiState) { viewModel.getEvent(it) }
    }

    if (action is BodyAction.ShowError) {
        ErrorDialog(message = (action as BodyAction.ShowError).message) {
            viewModel.getEvent(BodyEvent.ErrorShowed)
        }
    }

    LaunchedEffect(key1 = action) {
        when (action) {
            BodyAction.NavigateToImages -> {
                navController.navigate(Screen.ImagesScreen.route)
            }
            BodyAction.NavigateToProgress -> {
                navController.navigate(Screen.ProgressScreen.route)
            }
            BodyAction.NavigateToStatistics -> {
                navController.navigate(Screen.StatisticsScreen.route)
            }
            else -> {}
        }
    }
}