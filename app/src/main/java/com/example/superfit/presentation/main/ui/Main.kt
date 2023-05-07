package com.example.superfit.presentation.main.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.superfit.R
import com.example.superfit.domain.model.TrainingType
import com.example.superfit.presentation.main.models.MainEvent

@Composable
fun Main(
    weight: String?,
    height: String?,
    lastExercises: Pair<TrainingType?, TrainingType?>,
    getEvent: (MainEvent) -> Unit
) {
    Column(modifier = Modifier
        .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.fillMaxHeight(0.2f))

        Column(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.92f)
            .background(
                MaterialTheme.colorScheme.onPrimary,
                RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
            )
            .padding(start = 16.dp, end = 16.dp)
            .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.padding(12.dp))
            
            Text(
                text = stringResource(R.string.my_body),
                style = MaterialTheme.typography.titleSmall,
                color = Color.Black
            )
            Spacer(modifier = Modifier.padding(8.dp))

            BodyCard(weight = weight, height = height) { getEvent(it) }

            Spacer(modifier = Modifier.padding(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.last_exercises),
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.Black
                )
                Text(
                    text = stringResource(R.string.see_all),
                    modifier = Modifier
                        .clickable { getEvent(MainEvent.ShowAllExercises) },
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.tertiaryContainer
                )
            }

            Spacer(modifier = Modifier.padding(8.dp))

            ExerciseCard(type = lastExercises.first ?: TrainingType.PUSH_UP) { getEvent(it) }

            Spacer(modifier = Modifier.padding(8.dp))
            
            ExerciseCard(type = lastExercises.second ?: TrainingType.PLANK) { getEvent(it) }

            Spacer(modifier = Modifier.padding(4.dp))
        }

        Row(
            modifier = Modifier.fillMaxSize()
                .background(MaterialTheme.colorScheme.onPrimary)
                .padding(start = 16.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.arrow_left),
                contentDescription = null,
                modifier = Modifier.align(Alignment.CenterVertically)
                    .padding(top = 2.dp)
                    .clickable { getEvent(MainEvent.SignOut) },
                tint = Color.Black
            )
            Spacer(modifier = Modifier.padding(4.dp))
            Text(
                text = stringResource(R.string.sign_out),
                modifier = Modifier.align(Alignment.CenterVertically)
                    .clickable { getEvent(MainEvent.SignOut) },
                style = MaterialTheme.typography.titleSmall,
                color = Color.Black
            )
        }
    }
}