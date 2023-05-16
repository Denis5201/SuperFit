package com.example.superfit.presentation.graphics.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.superfit.domain.model.TrainingType
import com.example.superfit.presentation.graphics.models.StatisticsEvent
import com.example.superfit.presentation.main.ui.getExerciseTitle
import com.example.superfit.ui.theme.BackgroundDark
import com.example.superfit.ui.theme.White12

@Composable
fun Chip(
    type: TrainingType,
    isSelected: Boolean,
    getEvent: (StatisticsEvent) -> Unit
) {
    val titleId = getExerciseTitle(type)

    Text(
        text = stringResource(titleId),
        modifier = Modifier
            .clickable {
                getEvent(StatisticsEvent.SelectTrainingType(type))
            }
            .background(BackgroundDark, RoundedCornerShape(16.dp))
            .border(
                width = 1.dp,
                color = if (isSelected) {
                    MaterialTheme.colorScheme.primaryContainer
                } else {
                    White12
                },
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 12.dp, vertical = 6.dp),
        style = MaterialTheme.typography.bodySmall,
        color = if (isSelected) {
            MaterialTheme.colorScheme.primaryContainer
        } else {
            MaterialTheme.colorScheme.onPrimary
        }
    )
}