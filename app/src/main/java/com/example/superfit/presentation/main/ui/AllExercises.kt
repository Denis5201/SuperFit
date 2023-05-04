package com.example.superfit.presentation.main.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.superfit.R
import com.example.superfit.domain.model.TrainingType
import com.example.superfit.presentation.main.models.MainEvent

@Composable
fun AllExercises(
    getEvent: (MainEvent) -> Unit
) {
    BackHandler {
        getEvent(MainEvent.BackToMain)
    }

    Column(modifier = Modifier
        .statusBarsPadding()
        .fillMaxSize()
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.arrow_left),
            contentDescription = null,
            modifier = Modifier
                .padding(start = 16.dp, top = 20.dp)
                .clickable { getEvent(MainEvent.BackToMain) },
            tint = MaterialTheme.colorScheme.onPrimary
        )

        Spacer(modifier = Modifier.fillMaxHeight(0.128f))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(
                    MaterialTheme.colorScheme.onPrimary,
                    RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                )
                .padding(start = 16.dp, end = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.padding(12.dp))

            Text(
                text = stringResource(R.string.exercises),
                style = MaterialTheme.typography.titleSmall,
                color = Color.Black
            )
            Spacer(modifier = Modifier.padding(8.dp))

            ExerciseCard(type = TrainingType.PUSH_UP) { getEvent(it) }

            Spacer(modifier = Modifier.padding(8.dp))

            ExerciseCard(type = TrainingType.PLANK) { getEvent(it) }

            Spacer(modifier = Modifier.padding(8.dp))

            ExerciseCard(type = TrainingType.SQUATS) { getEvent(it) }

            Spacer(modifier = Modifier.padding(8.dp))

            ExerciseCard(type = TrainingType.CRUNCH) { getEvent(it) }

            Spacer(modifier = Modifier.padding(8.dp))

            ExerciseCard(type = TrainingType.RUNNING) { getEvent(it) }

            Spacer(modifier = Modifier.padding(14.dp))
        }
    }
}