package com.example.superfit.presentation.body.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.superfit.R
import com.example.superfit.presentation.body.models.BodyEvent

@Composable
fun BigIconAddImage(
    getEvent: (BodyEvent) -> Unit
) {
    Box(
        modifier = Modifier
            .padding(start = 32.dp, top = 16.dp)
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
}