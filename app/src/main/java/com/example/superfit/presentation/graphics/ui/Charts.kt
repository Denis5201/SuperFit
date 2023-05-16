package com.example.superfit.presentation.graphics.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.superfit.R
import com.example.superfit.domain.model.TrainingType
import com.example.superfit.presentation.graphics.models.StatisticsEvent
import com.example.superfit.presentation.graphics.models.StatisticsUiState

@Composable
fun Charts(
    uiState: StatisticsUiState.ShowCharts,
    getEvent: (StatisticsEvent) -> Unit
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(start = 16.dp, end = 16.dp)
        .verticalScroll(rememberScrollState())
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.arrow_left),
            contentDescription = null,
            modifier = Modifier
                .statusBarsPadding()
                .padding(top = 20.dp)
                .clickable { getEvent(StatisticsEvent.BackToMyBody) },
            tint = MaterialTheme.colorScheme.onPrimary
        )

        Spacer(modifier = Modifier.padding(8.dp))

        Text(
            text = stringResource(R.string.weight),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onPrimary
        )

        if (uiState.weightData.size > 1) {
            LineChart(
                weightList = uiState.weightData,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(170.dp)
            )
        } else {
            Text(
                text = stringResource(R.string.one_weight_list_data),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }

        Spacer(modifier = Modifier.padding(10.dp))

        Text(
            text = stringResource(R.string.training),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onPrimary
        )

        Spacer(modifier = Modifier.padding(10.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
        ) {
            Chip(
                type = TrainingType.PUSH_UP,
                isSelected = uiState.selectedTrainingType == TrainingType.PUSH_UP,
                getEvent = getEvent
            )
            Spacer(modifier = Modifier.padding(10.dp))
            Chip(
                type = TrainingType.PLANK,
                isSelected = uiState.selectedTrainingType == TrainingType.PLANK,
                getEvent = getEvent
            )
            Spacer(modifier = Modifier.padding(10.dp))
            Chip(
                type = TrainingType.CRUNCH,
                isSelected = uiState.selectedTrainingType == TrainingType.CRUNCH,
                getEvent = getEvent
            )
            Spacer(modifier = Modifier.padding(10.dp))
            Chip(
                type = TrainingType.SQUATS,
                isSelected = uiState.selectedTrainingType == TrainingType.SQUATS,
                getEvent = getEvent
            )
            Spacer(modifier = Modifier.padding(10.dp))
            Chip(
                type = TrainingType.RUNNING,
                isSelected = uiState.selectedTrainingType == TrainingType.RUNNING,
                getEvent = getEvent
            )
        }

        Spacer(modifier = Modifier.padding(10.dp))

        if (uiState.trainingList.size > 1) {
            BarChart(
                trainingList = uiState.trainingList,
                type = uiState.selectedTrainingType,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
            )
        } else {
            Text(
                text = stringResource(R.string.one_training),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }

        Spacer(modifier = Modifier.padding(2.dp))
    }
}