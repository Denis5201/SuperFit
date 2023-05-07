package com.example.superfit.presentation.body.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.superfit.R
import com.example.superfit.presentation.body.models.BodyEvent
import com.example.superfit.presentation.body.models.BodyUiState

@Composable
fun UserBody(
    uiState: BodyUiState,
    getEvent: (BodyEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .statusBarsPadding()
            .padding(start = 16.dp, end = 16.dp)
    ) {
        Spacer(modifier = Modifier.padding(12.dp))

        BodyParams(uiState = uiState, getEvent = getEvent)

        Spacer(modifier = Modifier.padding(24.dp))

        UserPhotoProgress(uiState = uiState, getEvent = getEvent)

        Spacer(modifier = Modifier.padding(12.dp))

        Row(
            modifier = Modifier.clickable { getEvent(BodyEvent.NavigateToProgress) },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.train_progress),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(modifier = Modifier.padding(4.dp))
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.arrow_right),
                contentDescription = null,
                modifier = Modifier.padding(top = 2.dp),
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }

        Spacer(modifier = Modifier.padding(12.dp))

        Row(
            modifier = Modifier.clickable { getEvent(BodyEvent.NavigateToStatistics) },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.statistics),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(modifier = Modifier.padding(4.dp))
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.arrow_right),
                contentDescription = null,
                modifier = Modifier.padding(top = 2.dp),
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }

        Spacer(modifier = Modifier.padding(12.dp))
    }
}