package com.example.superfit.presentation.training.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.superfit.R
import com.example.superfit.domain.model.TrainingType
import com.example.superfit.presentation.training.models.TrainingEvent
import com.example.superfit.presentation.training.models.TrainingUiState

@Composable
fun UnSuccessScreen(
    uiState: TrainingUiState.TrainingUnSuccess,
    getEvent: (TrainingEvent) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {

        CircularProgressIndicator(
            progress = 1f,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .padding(horizontal = 72.dp)
                .aspectRatio(1f),
            strokeWidth = 4.dp,
            color = MaterialTheme.colorScheme.primary,
            backgroundColor = MaterialTheme.colorScheme.background
        )

        Column(
            modifier = Modifier.align(Alignment.Center).padding(horizontal = 100.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val unit = when(uiState.type) {
                TrainingType.PLANK -> R.string.x_seconds
                TrainingType.RUNNING -> R.string.x_meters
                else -> R.string.x_times
            }
            Text(
                text = stringResource(unit, uiState.remains),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Text(
                text = stringResource(R.string.un_success_text),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Center
            )
        }

        Button(
            onClick = {
                getEvent(TrainingEvent.BackToMainScreen)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
                .align(Alignment.BottomCenter)
        ) {
            Text(
                text = stringResource(R.string.go_home),
                style = MaterialTheme.typography.titleSmall
            )
        }
    }
}