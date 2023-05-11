package com.example.superfit.presentation.images

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.superfit.presentation.AppProgressBar
import com.example.superfit.presentation.ErrorDialog
import com.example.superfit.presentation.images.models.ImagesAction
import com.example.superfit.presentation.images.models.ImagesEvent
import com.example.superfit.presentation.images.models.ImagesUiState
import com.example.superfit.presentation.images.ui.ShowImage
import com.example.superfit.presentation.images.ui.UserImageList

@Composable
fun ImagesScreen(
    navController: NavController,
    viewModel: ImagesViewModel
) {

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val action by viewModel.action.collectAsStateWithLifecycle(null)

    when (val state = uiState.value) {
        ImagesUiState.Loading -> {
            AppProgressBar()
        }
        is ImagesUiState.ShowImageList -> {
            UserImageList(state) { viewModel.getEvent(it) }
        }
        is ImagesUiState.ShowImage -> {
            ShowImage(state.userImage) { viewModel.getEvent(it) }
        }
    }

    if (action is ImagesAction.ShowError) {
        ErrorDialog(message = (action as ImagesAction.ShowError).message) {
            viewModel.getEvent(ImagesEvent.ErrorShowed)
        }
    }

    LaunchedEffect(key1 = action) {
        when (action) {
            ImagesAction.NavigateBack -> {
                navController.popBackStack()
            }
            else -> {}
        }
    }
}