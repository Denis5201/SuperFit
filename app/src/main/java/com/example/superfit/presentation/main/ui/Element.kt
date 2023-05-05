package com.example.superfit.presentation.main.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.superfit.Constants
import com.example.superfit.R
import com.example.superfit.domain.model.TrainingType
import com.example.superfit.presentation.main.models.MainEvent

@Composable
fun ExerciseCard(
    type: TrainingType,
    getEvent: (MainEvent) -> Unit
) {
    val imageId = getExerciseImageId(type)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
            .clip(RoundedCornerShape(8.dp))
            .clickable { getEvent(MainEvent.NavigateToTraining(type)) }
    ) {
        Box(modifier = Modifier
            .weight(1f)
        ) {
            Image(
                bitmap = ImageBitmap.imageResource(imageId),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.horizontalGradient(
                            0.0f to Color.Transparent,
                            0.8f to Color.Transparent,
                            1f to MaterialTheme.colorScheme.background
                        )
                    )
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(start = 16.dp, end = 16.dp)
        ) {
            Spacer(modifier = Modifier.padding(4.dp))
            Text(
                text = type.title,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )

            Spacer(modifier = Modifier.padding(2.dp))
            Text(
                text = stringResource(getExerciseDescription(type)), 
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.secondary
            )
            Spacer(modifier = Modifier.padding(5.dp))
        }
    }
}

@Composable
fun BodyCard(
    weight: Int?,
    height: Int?,
    getEvent: (MainEvent) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
            .clip(RoundedCornerShape(8.dp))
    ) {
        Box(modifier = Modifier
            .weight(1f)
        ) {
            Image(
                bitmap = ImageBitmap.imageResource(R.drawable.body),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.horizontalGradient(
                            0.0f to Color.Transparent,
                            0.8f to Color.Transparent,
                            1f to MaterialTheme.colorScheme.background
                        )
                    )
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(start = 16.dp, end = 16.dp)
        ) {
            Spacer(modifier = Modifier.padding(6.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.weight),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    text = weight?.toString() ?: Constants.UNDEFINED,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
            Spacer(modifier = Modifier.padding(6.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.height),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    text = height?.toString() ?: Constants.UNDEFINED,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
            Spacer(modifier = Modifier.padding(13.dp))

            Row(
                modifier = Modifier.clickable { getEvent(MainEvent.NavigateToBody) },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.details),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.secondary
                )
                Spacer(modifier = Modifier.padding(2.dp))
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.arrow_small_right),
                    contentDescription = null,
                    modifier = Modifier.padding(top = 2.dp),
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
            Spacer(modifier = Modifier.padding(4.dp))
        }
    }
}

fun getExerciseImageId(type: TrainingType): Int {
    return when(type) {
        TrainingType.CRUNCH -> R.drawable.crunch
        TrainingType.SQUATS -> R.drawable.squats
        TrainingType.PUSH_UP -> R.drawable.push_ups
        TrainingType.PLANK -> R.drawable.plank
        TrainingType.RUNNING -> R.drawable.running
    }
}
fun getExerciseDescription(type: TrainingType): Int {
    return when(type) {
        TrainingType.CRUNCH -> R.string.crunch_desc
        TrainingType.SQUATS -> R.string.squats_desc
        TrainingType.PUSH_UP -> R.string.push_ups_desc
        TrainingType.PLANK -> R.string.plank_desc
        TrainingType.RUNNING -> R.string.running_desc
    }
}