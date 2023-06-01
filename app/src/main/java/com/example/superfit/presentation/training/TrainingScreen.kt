package com.example.superfit.presentation.training

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.superfit.presentation.AppProgressBar
import com.example.superfit.presentation.ErrorDialog
import com.example.superfit.presentation.main.ui.getExerciseTitle
import com.example.superfit.presentation.training.models.TrainingAction
import com.example.superfit.presentation.training.models.TrainingEvent
import com.example.superfit.presentation.training.models.TrainingUiState
import com.example.superfit.presentation.training.ui.Exercise
import com.example.superfit.presentation.training.ui.SuccessScreen
import com.example.superfit.presentation.training.ui.UnSuccessScreen

@Composable
fun TrainingScreen(
    navController: NavController,
    viewModel: TrainingViewModel
) {

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val action by viewModel.action.collectAsStateWithLifecycle(null)

    val titleId = getExerciseTitle(viewModel.type)
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Text(
            text = stringResource(titleId),
            modifier = Modifier
                .statusBarsPadding()
                .padding(top = 56.dp),
            style = MaterialTheme.typography.titleMedium
        )
    }


    when(val state = uiState.value) {
        TrainingUiState.Loading -> {
            AppProgressBar()
        }
        is TrainingUiState.Training -> {
            Exercise(uiState = state) { viewModel.getEvent(it) }
        }
        TrainingUiState.TrainingSuccess -> {
            SuccessScreen { viewModel.getEvent(it) }
        }
        is TrainingUiState.TrainingUnSuccess -> {
            UnSuccessScreen(state) { viewModel.getEvent(it) }
        }
    }

    if (action is TrainingAction.ShowError) {
        ErrorDialog(message = (action as TrainingAction.ShowError).message) {
            viewModel.getEvent(TrainingEvent.ErrorShowed)
        }
    }

    LaunchedEffect(key1 = action) {
        when (action) {
            TrainingAction.NavigateBack -> {
                navController.popBackStack()
            }
            else -> {}
        }
    }
}