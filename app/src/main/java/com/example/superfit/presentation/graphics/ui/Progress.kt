package com.example.superfit.presentation.graphics.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
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
import com.example.superfit.presentation.graphics.models.ProgressEvent
import com.example.superfit.presentation.graphics.models.ProgressUiState

@Composable
fun Progress(
    uiState: ProgressUiState.ShowProgress,
    getEvent: (ProgressEvent) -> Unit
) {
    BoxWithConstraints(modifier = Modifier
        .fillMaxSize()
        .statusBarsPadding()
    ) {
        val maxWidth = maxWidth
        Row(modifier = Modifier.padding(start = 16.dp, top = 10.dp)) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.arrow_left),
                contentDescription = null,
                modifier = Modifier
                    .padding(top = 12.dp)
                    .clickable { getEvent(ProgressEvent.BackToMyBody) },
                tint = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(modifier = Modifier.padding(4.dp))
            Text(
                text = stringResource(R.string.train_progress),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }

        UpperBody(maxWidth = maxWidth, uiState = uiState)

        LowerBody(maxWidth = maxWidth, uiState = uiState)
    }
}