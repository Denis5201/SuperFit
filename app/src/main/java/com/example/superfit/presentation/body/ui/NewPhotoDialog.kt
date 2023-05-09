package com.example.superfit.presentation.body.ui

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.superfit.R
import com.example.superfit.presentation.body.BodyViewModel
import com.example.superfit.presentation.body.models.BodyEvent
import java.io.File

@Composable
fun NewPhotoDialog(
    galleryLauncher: ManagedActivityResultLauncher<String, Uri?>,
    cameraLauncher: ManagedActivityResultLauncher<Uri, Boolean>,
    permissionLauncher: ManagedActivityResultLauncher<String, Boolean>,
    getEvent: (BodyEvent) -> Unit
) {
    val context = LocalContext.current

    Dialog(onDismissRequest = { getEvent(BodyEvent.ClosePhotoDialog) }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.tertiary, RoundedCornerShape(2.dp))
                .padding(16.dp),
        ) {
            Text(
                text = stringResource(R.string.choosing_where_take_photo),
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
                    modifier = Modifier.clickable { getEvent(BodyEvent.ClosePhotoDialog) },
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.padding(start = 24.dp))

                Text(
                    text =  stringResource(R.string.gallery),
                    modifier = Modifier.clickable {
                        galleryLauncher.launch("image/*")
                    },
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.padding(start = 24.dp))
                Text(
                    text =  stringResource(R.string.camera),
                    modifier = Modifier.clickable {
                        val permissionCheck =
                            ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                            val myUri = createTempFileAndGetUri(context)
                            getEvent(BodyEvent.PhotoFromCamera(myUri))
                            cameraLauncher.launch(myUri)
                        } else {
                            permissionLauncher.launch(Manifest.permission.CAMERA)
                        }
                    },
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

fun createTempFileAndGetUri(context: Context): Uri {
    val file = File.createTempFile(
        BodyViewModel.IMAGE_NAME,
        BodyViewModel.JPEG, context.cacheDir
    )
    return FileProvider.getUriForFile(
        context,
        context.packageName + ".provider",
        file)
}