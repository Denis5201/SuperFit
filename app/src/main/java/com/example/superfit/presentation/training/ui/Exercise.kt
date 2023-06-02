package com.example.superfit.presentation.training.ui

import android.os.CountDownTimer
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.superfit.R
import com.example.superfit.domain.model.TrainingType
import com.example.superfit.presentation.training.models.TrainingEvent
import com.example.superfit.presentation.training.models.TrainingUiState

@Composable
fun Exercise(
    uiState: TrainingUiState.Training,
    getEvent: (TrainingEvent) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        if (uiState.type == TrainingType.SQUATS) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.arrow_left),
                contentDescription = null,
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(start = 16.dp, top = 20.dp)
                    .clickable { getEvent(TrainingEvent.BackToMainScreen) },
                tint = MaterialTheme.colorScheme.onPrimary
            )
        } else {
            Button(
                onClick = {
                    when (uiState.type) {
                        TrainingType.CRUNCH -> getEvent(TrainingEvent.SaveSuccessAndBack)
                        else -> getEvent(TrainingEvent.ShowUnSuccess(uiState.count))
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
                    .align(Alignment.BottomCenter)
            ) {
                Text(
                    text = stringResource(R.string.finish),
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }

        CircularProgressIndicator(
            progress = uiState.percent,
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
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = uiState.count.toString(),
                style = MaterialTheme.typography.titleLarge
            )
            val labelId = when (uiState.type) {
                TrainingType.CRUNCH -> R.string.need_to_do
                TrainingType.PLANK -> R.string.seconds_left
                TrainingType.RUNNING -> R.string.meters_passed
                else -> R.string.times_left
            }
            Text(
                text = stringResource(labelId),
                style = MaterialTheme.typography.labelMedium,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }

    when (uiState.type) {
        TrainingType.SQUATS -> {
            if (uiState.count == 0) {
                getEvent(TrainingEvent.ShowSuccess)
            }
            LinearSensor(1, getEvent)
        }
        TrainingType.PUSH_UP -> {
            if (uiState.count == 0) {
                getEvent(TrainingEvent.ShowSuccess)
            }
            LinearSensor(2, getEvent)
        }
        TrainingType.RUNNING -> {
            if (uiState.count == 0) {
                getEvent(TrainingEvent.ShowSuccess)
            }
            StepSensor(getEvent)
        }
        else -> {}
    }

    if (uiState.type == TrainingType.PLANK) {
        if (uiState.isDialogOpen) {
            StartTrainingDialog(count = uiState.count, getEvent = getEvent)
        } else {
            DisposableEffect(key1 = Unit) {
                val timer = object : CountDownTimer(
                    uiState.count * 1000L, 1000L
                ) {
                    override fun onTick(p0: Long) {
                        getEvent(TrainingEvent.DecreaseCount(1))
                    }

                    override fun onFinish() {
                        getEvent(TrainingEvent.ShowSuccess)
                    }
                }.start()

                onDispose {
                    timer.cancel()
                }
            }
        }
    }
}