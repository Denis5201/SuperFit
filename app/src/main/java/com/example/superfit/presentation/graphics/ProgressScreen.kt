package com.example.superfit.presentation.graphics

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.superfit.R
import com.example.superfit.presentation.AppProgressBar
import com.example.superfit.presentation.ErrorDialog
import com.example.superfit.presentation.graphics.models.ProgressAction
import com.example.superfit.presentation.graphics.models.ProgressEvent
import com.example.superfit.presentation.graphics.models.ProgressUiState
import com.example.superfit.presentation.graphics.ui.Progress

@Composable
fun ProgressScreen(
    navController: NavController,
    viewModel: ProgressViewModel
) {

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val action by viewModel.action.collectAsStateWithLifecycle(null)

    Box(modifier = Modifier
        .statusBarsPadding()
        .fillMaxSize()) {
        Image(
            bitmap = ImageBitmap.imageResource(R.drawable.progress_man),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
    }

    when(val state = uiState.value) {
        ProgressUiState.Loading -> {
            AppProgressBar()
        }
        is ProgressUiState.ShowProgress -> {
            Progress(uiState = state) { viewModel.getEvent(it) }
        }
    }

    if (action is ProgressAction.ShowError) {
        ErrorDialog(message = (action as ProgressAction.ShowError).message) {
            viewModel.getEvent(ProgressEvent.ErrorShowed)
        }
    }

    LaunchedEffect(key1 = action) {
        when (action) {
            ProgressAction.BackToMyBody -> {
                navController.popBackStack()
            }
            else -> {}
        }
    }
}