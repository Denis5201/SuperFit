package com.example.superfit.presentation.body.ui

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.exifinterface.media.ExifInterface
import com.example.superfit.Constants
import com.example.superfit.R
import com.example.superfit.presentation.body.models.BodyEvent
import com.example.superfit.presentation.body.models.BodyUiState
import java.io.File

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
            BigIconAddImage(getEvent = getEvent)
        } else {
            Box(modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.48f)) {
                Image(
                    bitmap = uiState.userPhotoProgress.first!!.bitmap.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillWidth
                )
                Text(
                    text = uiState.userPhotoProgress.first!!.date.format(Constants.DATE_FORMAT),
                    modifier = Modifier
                        .padding(start = 8.dp, bottom = 8.dp)
                        .height(24.dp)
                        .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(12.dp))
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                        .align(Alignment.BottomStart),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }

        Spacer(modifier = Modifier.padding(2.dp))

        if (uiState.userPhotoProgress.second == null && uiState.userPhotoProgress.first != null) {
            BigIconAddImage(getEvent = getEvent)
        } else if (uiState.userPhotoProgress.second != null) {
            Box(modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()) {
                Image(
                    bitmap = uiState.userPhotoProgress.second!!.bitmap.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillWidth
                )
                Text(
                    text = uiState.userPhotoProgress.second!!.date.format(Constants.DATE_FORMAT),
                    modifier = Modifier
                        .padding(start = 8.dp, bottom = 4.dp)
                        .height(24.dp)
                        .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(12.dp))
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                        .align(Alignment.BottomStart),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.add_image),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(end = 8.dp, bottom = 4.dp)
                        .background(MaterialTheme.colorScheme.onPrimary, CircleShape)
                        .padding(4.dp)
                        .align(Alignment.BottomEnd)
                        .clickable { getEvent(BodyEvent.OpenPhotoDialog) },
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }

    val context = LocalContext.current
    val getGalleryImage = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) {
        if (it != null) {
            val bitmap = context.contentResolver.openInputStream(it)?.use { stream ->
                BitmapFactory.decodeStream(stream)
            }
            val normalizedBitmap = context.contentResolver.openInputStream(it)?.use { stream ->
                val exif = ExifInterface(stream)
                normalizeRotationImage(bitmap!!, exif)
            }

            val scaledBitmap = scalingGalleryBitmap(normalizedBitmap!!)
            getEvent(BodyEvent.NewPhoto(scaledBitmap))
        }
    }
    val getCameraImage = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) {
        if (it) {
            val filePath = "${context.cacheDir.absolutePath}/${uiState.uri.pathSegments.last()}"

            val file = File(filePath)

            val bitmap = decodeFileToBitmap(filePath)
            val exif = ExifInterface(file)
            val adjustedBitmap = normalizeRotationImage(bitmap, exif)

            file.delete()
            getEvent(BodyEvent.NewPhoto(adjustedBitmap))
        }
    }
    val permission = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            val myUri = createTempFileAndGetUri(context)
            getEvent(BodyEvent.PhotoFromCamera(myUri))
            getCameraImage.launch(myUri)
        }
    }

    if (uiState.choosingImage) {
        NewPhotoDialog(
            galleryLauncher = getGalleryImage,
            cameraLauncher = getCameraImage,
            permissionLauncher = permission,
            getEvent = getEvent
        )
    }

}

private fun decodeFileToBitmap(filePath: String): Bitmap {
    val option = BitmapFactory.Options()
    option.inJustDecodeBounds = true

    BitmapFactory.decodeFile(filePath, option)
    val height = option.outHeight
    val width = option.outWidth

    option.inJustDecodeBounds = false
    var scale = 1
    while (height / scale > 1000 || width / scale > 1000) {
        scale *= 2
    }
    option.inSampleSize = scale

    return BitmapFactory.decodeFile(filePath, option)
}

private fun normalizeRotationImage(image: Bitmap, exif: ExifInterface): Bitmap {
    val rotation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
    val rotationInDegrees = exifToDegrees(rotation)

    val matrix = Matrix()
    if (rotation != 0) {
        matrix.preRotate(rotationInDegrees.toFloat())
    }

    return Bitmap.createBitmap(image, 0, 0, image.width, image.height, matrix, true)
}

private fun exifToDegrees(exifOrientation: Int): Int {
    return when (exifOrientation) {
        ExifInterface.ORIENTATION_ROTATE_90 -> 90
        ExifInterface.ORIENTATION_ROTATE_180 -> 180
        ExifInterface.ORIENTATION_ROTATE_270 -> 270
        else -> 0
    }
}

fun scalingGalleryBitmap(image: Bitmap): Bitmap {
    val oldWidth = image.width
    val oldHeight = image.height
    return if (oldWidth < 1000 || oldHeight < 1000) {
        image
    } else {
        val scale = maxOf(oldWidth, oldHeight) / 1000 + 1
        Bitmap.createScaledBitmap(image, oldWidth / scale, oldHeight / scale, true)
    }
}