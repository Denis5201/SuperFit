package com.example.superfit.presentation.body.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.superfit.R
import com.example.superfit.presentation.body.models.BodyEvent
import com.example.superfit.presentation.body.models.BodyUiState

@Composable
fun UserPhotoProgress(
    uiState: BodyUiState,
    getEvent: (BodyEvent) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(R.string.my_progress),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onPrimary
        )
        Text(
            text = stringResource(R.string.see_all),
            modifier = Modifier
                .clickable { getEvent(BodyEvent.NavigateToImages) },
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.secondary
        )
    }

    Spacer(modifier = Modifier.padding(4.dp))

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp),
    ) {
        if (uiState.userPhotoProgress.first == null) {
            Box(
                modifier = Modifier
                    .padding(start = 16.dp, top = 16.dp)
                    .size(110.dp)
                    .background(MaterialTheme.colorScheme.onPrimary, CircleShape)
                    .clickable { getEvent(BodyEvent.OpenPhotoDialog) },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.add_image),
                    contentDescription = null,
                    modifier = Modifier
                        .size(80.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        } else {
            Box(modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.48f)) {
                Image(
                    bitmap = uiState.userPhotoProgress.first!!.bitmap.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
                Text(
                    text = uiState.userPhotoProgress.first!!.date.toString(),
                    modifier = Modifier
                        .height(24.dp)
                        .padding(start = 8.dp)
                        .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(12.dp))
                        .align(Alignment.BottomStart),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }

        if (uiState.userPhotoProgress.second == null && uiState.userPhotoProgress.first != null) {
            Box(
                modifier = Modifier
                    .padding(start = 16.dp, top = 16.dp)
                    .size(110.dp)
                    .background(MaterialTheme.colorScheme.onPrimary, CircleShape)
                    .clickable { getEvent(BodyEvent.OpenPhotoDialog) },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.add_image),
                    contentDescription = null,
                    modifier = Modifier
                        .size(80.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        } else if (uiState.userPhotoProgress.second != null) {
            Box(modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.48f)) {
                Image(
                    bitmap = uiState.userPhotoProgress.second!!.bitmap.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
                Text(
                    text = uiState.userPhotoProgress.second!!.date.toString(),
                    modifier = Modifier
                        .height(24.dp)
                        .padding(start = 8.dp)
                        .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(12.dp))
                        .align(Alignment.BottomStart),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.add_image),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .background(MaterialTheme.colorScheme.onPrimary, CircleShape)
                        .align(Alignment.BottomEnd)
                        .clickable { getEvent(BodyEvent.OpenPhotoDialog) },
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}