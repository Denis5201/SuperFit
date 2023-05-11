package com.example.superfit.presentation.images.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.superfit.R
import com.example.superfit.presentation.images.models.ImagesEvent

@Composable
fun DeletePhotoDialog(
    photoIndexes: Pair<Int?, Int?>,
    getEvent: (ImagesEvent) -> Unit
) {
    Dialog(onDismissRequest = { getEvent(ImagesEvent.CloseDeleteDialog) }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.tertiary, RoundedCornerShape(2.dp))
                .padding(16.dp),
        ) {
            Text(
                text = stringResource(R.string.delete_this_photo),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )

            Spacer(modifier = Modifier.padding(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = stringResource(R.string.cancel),
                    modifier = Modifier.clickable { getEvent(ImagesEvent.CloseDeleteDialog) },
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.padding(start = 24.dp))

                Text(
                    text = stringResource(R.string.delete),
                    modifier = Modifier.clickable {
                        getEvent(ImagesEvent.DeletePhoto(photoIndexes.first!!, photoIndexes.second!!))
                    },
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}