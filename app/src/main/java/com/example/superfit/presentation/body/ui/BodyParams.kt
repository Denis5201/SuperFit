package com.example.superfit.presentation.body.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.superfit.R
import com.example.superfit.presentation.body.BodyViewModel
import com.example.superfit.presentation.body.models.BodyEvent
import com.example.superfit.presentation.body.models.BodyUiState

@Composable
fun BodyParams(
    uiState: BodyUiState,
    getEvent: (BodyEvent) -> Unit
) {
    Text(
        text = stringResource(R.string.my_body),
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.onPrimary
    )

    Spacer(modifier = Modifier.padding(12.dp))

    var smallWeight = false
    var smallHeight = false
    val weightText = if (uiState.weight.length > 3) {
        smallWeight = true
        stringResource(R.string.undefined)
    } else {
        stringResource(R.string.weight_value, uiState.weight)
    }
    val heightText = if (uiState.weight.length > 3) {
        smallHeight = true
        stringResource(R.string.undefined)
    } else {
        stringResource(R.string.height_value, uiState.height)
    }

    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = weightText,
                style = if (smallWeight) {
                    MaterialTheme.typography.titleSmall
                } else {
                    MaterialTheme.typography.titleMedium
                       },
                color = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(modifier = Modifier.padding(2.dp))
            Text(
                text = stringResource(R.string.edit),
                modifier = Modifier.clickable {
                    getEvent(BodyEvent.OpenChangingParamsDialog(BodyViewModel.WEIGHT_DIALOG))
                },
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.secondary
            )
        }

        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = heightText,
                style = if (smallHeight) {
                    MaterialTheme.typography.titleSmall
                } else {
                    MaterialTheme.typography.titleMedium
                },
                color = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(modifier = Modifier.padding(2.dp))
            Text(
                text = stringResource(R.string.edit),
                modifier = Modifier.clickable {
                    getEvent(BodyEvent.OpenChangingParamsDialog(BodyViewModel.HEIGHT_DIALOG))
                },
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }

    if (uiState.changingWeight) {
        ChangeParamsDialog(
            title = stringResource(R.string.weight),
            state = uiState,
            getEvent = getEvent
        )
    }
    if (uiState.changingHeight) {
        ChangeParamsDialog(
            title = stringResource(R.string.height),
            state = uiState,
            getEvent = getEvent
        )
    }
}