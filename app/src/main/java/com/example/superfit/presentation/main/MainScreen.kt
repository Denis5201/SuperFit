package com.example.superfit.presentation.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.superfit.R
import com.example.superfit.Screen
import com.example.superfit.presentation.AppProgressBar
import com.example.superfit.presentation.ErrorDialog
import com.example.superfit.presentation.main.models.MainAction
import com.example.superfit.presentation.main.models.MainEvent
import com.example.superfit.presentation.main.models.MainUiState
import com.example.superfit.presentation.main.ui.AllExercises
import com.example.superfit.presentation.main.ui.Main

@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainViewModel
) {

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val action = viewModel.action.collectAsStateWithLifecycle(null)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.22f),
        contentAlignment = Alignment.Center
    ) {
        Image(
            bitmap = ImageBitmap.imageResource(id = R.drawable.main_header),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.titleMedium
        )
    }

    when(val state = uiState.value) {
        MainUiState.Loading -> {
            AppProgressBar()
        }
        is MainUiState.ShowMain -> {
            Main(
                weight = state.weight,
                height = state.height,
                lastExercises = state.lastExercises
            ) { viewModel.getEvent(it) }
        }
        MainUiState.ShowAllExercises -> {
            AllExercises { viewModel.getEvent(it) }
        }
    }

    if (action.value is MainAction.ShowError) {
        ErrorDialog(message = (action.value as MainAction.ShowError).message) {
            viewModel.getEvent(MainEvent.ErrorShowed)
        }
    }

    LaunchedEffect(key1 = action.value) {
        when (val actionValue = action.value) {
            is MainAction.NavigateToBody -> {
                navController.navigate("${Screen.BodyScreen.route}${actionValue.params}")
            }
            is MainAction.NavigateToTraining -> {
                navController.navigate("${Screen.TrainingScreen.route}${actionValue.params}")
            }
            MainAction.SignOut -> {
                navController.navigate(Screen.SignInScreen.route) {
                    popUpTo(navController.graph.id) {
                        inclusive = true
                    }
                }
            }
            else -> { }
        }
    }
}