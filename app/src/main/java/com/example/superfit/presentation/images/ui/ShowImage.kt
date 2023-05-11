package com.example.superfit.presentation.images.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.example.superfit.R
import com.example.superfit.presentation.UserImage
import com.example.superfit.presentation.body.BodyViewModel
import com.example.superfit.presentation.images.models.ImagesEvent

@Composable
fun ShowImage(
    image: UserImage,
    getEvent: (ImagesEvent) -> Unit
) {
    BackHandler {
        getEvent(ImagesEvent.BackToImageList)
    }

    var scale by remember { mutableStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    var size by remember { mutableStateOf(IntSize.Zero) }

    val zoomState = rememberTransformableState() { zoomChange, offsetChange, _ ->
        val scaleVal = scale * zoomChange
        scale = scaleVal.coerceIn(1f, 3f)

        val maxX = (size.width * (scale - 1) / 2f)
        val maxY = (size.height * (scale - 1) / 3f)

        val offsetVal = offset + offsetChange.times(scale)
        offset = Offset(
            offsetVal.x.coerceIn(-maxX, maxX),
            offsetVal.y.coerceIn(-maxY, maxY)
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            bitmap = image.bitmap.asImageBitmap(),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .onSizeChanged {
                    size = it
                }
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale,
                    translationX = offset.x,
                    translationY = offset.y
                )
                .transformable(zoomState)
        )

        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.arrow_left),
            contentDescription = null,
            modifier = Modifier
                .statusBarsPadding()
                .padding(start = 16.dp, top = 20.dp)
                .clickable { getEvent(ImagesEvent.BackToImageList) },
            tint = MaterialTheme.colorScheme.onPrimary
        )

        Text(
            text = image.date.format(BodyViewModel.DATE_FORMAT),
            modifier = Modifier
                .padding(start = 16.dp, bottom = 16.dp)
                .height(24.dp)
                .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(12.dp))
                .padding(horizontal = 12.dp, vertical = 4.dp)
                .align(Alignment.BottomStart),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}