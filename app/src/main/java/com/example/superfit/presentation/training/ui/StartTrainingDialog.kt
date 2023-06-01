package com.example.superfit.presentation.training.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.superfit.R
import com.example.superfit.presentation.training.models.TrainingEvent

@Composable
fun StartTrainingDialog(
    count: Int,
    getEvent: (TrainingEvent) -> Unit
) {
    Dialog(onDismissRequest = { getEvent(TrainingEvent.CloseStartDialog) }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.tertiary, RoundedCornerShape(2.dp))
                .padding(16.dp),
        ) {
            Text(
                text = stringResource(R.string.start_training),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )

            Spacer(modifier = Modifier.padding(5.dp))

            Text(
                text = stringResource(R.string.need_repeat, count),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.outlineVariant
            )

            Spacer(modifier = Modifier.padding(5.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = stringResource(R.string.later),
                    modifier = Modifier.clickable { getEvent(TrainingEvent.BackToMainScreen) },
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.padding(start = 50.dp))

                Text(
                    text = stringResource(R.string.go),
                    modifier = Modifier.clickable { getEvent(TrainingEvent.CloseStartDialog) },
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}