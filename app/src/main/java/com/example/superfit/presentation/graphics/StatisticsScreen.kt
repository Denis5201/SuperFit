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
import com.example.superfit.presentation.graphics.models.StatisticsAction
import com.example.superfit.presentation.graphics.models.StatisticsEvent
import com.example.superfit.presentation.graphics.models.StatisticsUiState
import com.example.superfit.presentation.graphics.ui.Charts

@Composable
fun StatisticsScreen(
    navController: NavController,
    viewModel: StatisticsViewModel
) {

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val action by viewModel.action.collectAsStateWithLifecycle(null)

    Box(modifier = Modifier
        .statusBarsPadding()
        .fillMaxSize()) {
        Image(
            bitmap = ImageBitmap.imageResource(R.drawable.statistics_background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
    }

    when(val state = uiState.value) {
        StatisticsUiState.Loading -> {
            AppProgressBar()
        }
        is StatisticsUiState.ShowCharts -> {
            Charts(state) { viewModel.getEvent(it) }
        }
    }

    if (action is StatisticsAction.ShowError) {
        ErrorDialog(message = (action as StatisticsAction.ShowError).message) {
            viewModel.getEvent(StatisticsEvent.ErrorShowed)
        }
    }

    LaunchedEffect(key1 = action) {
        when (action) {
            StatisticsAction.BackToMyBody -> {
                navController.popBackStack()
            }
            else -> {}
        }
    }
}