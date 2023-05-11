package com.example.superfit.presentation.images.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.superfit.R
import com.example.superfit.presentation.images.models.ImagesEvent
import com.example.superfit.presentation.images.models.ImagesUiState

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun UserImageList(
    uiState: ImagesUiState.ShowImageList,
    getEvent: (ImagesEvent) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.arrow_left),
            contentDescription = null,
            modifier = Modifier
                .statusBarsPadding()
                .padding(start = 16.dp, top = 20.dp)
                .clickable { getEvent(ImagesEvent.NavigateBack) },
            tint = MaterialTheme.colorScheme.onPrimary
        )

        Spacer(modifier = Modifier.padding(10.dp))

        if (uiState.galleryItemList.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                itemsIndexed(uiState.galleryItemList) { i, listImage ->
                    Text(
                        text = "${listImage.first().date.month}, ${listImage.first().date.year}",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.padding(8.dp))

                    FlowRow(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        listImage.forEachIndexed { index, userImage ->
                            Image(
                                bitmap = userImage.bitmap.asImageBitmap(),
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(end = 8.dp, bottom = 8.dp)
                                    .size(width = 96.dp, height = 96.dp)
                                    .pointerInput(Unit) {
                                        detectTapGestures(
                                            onLongPress = {
                                                getEvent(ImagesEvent.OpenDeleteDialog(i, index))
                                            },
                                            onTap = {
                                                getEvent(ImagesEvent.ShowImage(userImage))
                                            }
                                        )
                                    },
                                contentScale = ContentScale.Crop
                            )
                        }
                        Spacer(modifier = Modifier.padding(10.dp))
                    }
                }
            }
        }

        if (uiState.isOpenDeleteDialog) {
            DeletePhotoDialog(photoIndexes = uiState.deletingImageIndexes, getEvent = getEvent)
        }
    }
}