package com.example.superfit.presentation.graphics.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.superfit.R
import com.example.superfit.domain.model.TrainingType
import com.example.superfit.presentation.graphics.models.ProgressUiState
import com.example.superfit.presentation.main.ui.getExerciseTitle

@Composable
fun UpperBody(
    maxWidth: Dp,
    uiState: ProgressUiState.ShowProgress
) {
    val undefinedText = stringResource(R.string.undefined)
    val color = MaterialTheme.colorScheme.primary

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {

        Spacer(modifier = Modifier.fillMaxHeight(0.13f))

        Box(
            modifier = Modifier
                .padding(start = maxWidth.div(2.7f))
                .size(175.dp, 70.dp)
                .drawBehind {
                    drawCircle(
                        color = color,
                        radius = 4.dp.toPx(),
                        center = Offset(0f, size.height - 3.dp.toPx()),
                        style = Stroke(width = 2.dp.toPx())
                    )
                    drawLine(
                        color = color,
                        start = Offset(2.dp.toPx(), size.height - 5.dp.toPx()),
                        end = Offset(25.dp.toPx(), 23.dp.toPx()),
                        strokeWidth = 2.dp.toPx()
                    )
                    drawLine(
                        color = color,
                        start = Offset(25.dp.toPx(), 23.dp.toPx()),
                        end = Offset(size.width, 23.dp.toPx()),
                        strokeWidth = 2.dp.toPx()
                    )
                }
        ) {
            Column(modifier = Modifier.padding(start = 32.dp)) {
                Text(
                    text = stringResource(getExerciseTitle(TrainingType.PUSH_UP)),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.padding(3.dp))
                Text(
                    text = buildAnnotatedString {
                        append(stringResource(R.string.last_train))
                        withStyle(SpanStyle(fontWeight = FontWeight.W700)) {
                            val string = uiState.pushUps.first?.let {
                                stringResource(R.string.x_times, it)
                            } ?: undefinedText
                            append(string)
                        }
                    },
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.padding(2.dp))
                Row {
                    Text(
                        text = buildAnnotatedString {
                            append(stringResource(R.string.progress))
                            withStyle(SpanStyle(fontWeight = FontWeight.W700)) {
                                val string = uiState.pushUps.second?.let {
                                    "$it%"
                                } ?: undefinedText
                                append(string)
                            }
                        },
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    uiState.pushUps.second?.let {
                        if (it == 0) return@let
                        Icon(
                            imageVector = ImageVector.vectorResource(
                                id = if (it > 0) R.drawable.arrow_up else R.drawable.arrow_down
                            ),
                            contentDescription = null,
                            tint = if (it > 0) Color.Green else Color.Red
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.fillMaxHeight(0.1f))

        Box(
            modifier = Modifier
                .padding(start = maxWidth.div(2.7f))
                .size(185.dp, 70.dp)
                .drawBehind {
                    drawCircle(
                        color = color,
                        radius = 4.dp.toPx(),
                        center = Offset(-10.dp.toPx(), 23.dp.toPx()),
                        style = Stroke(width = 2.dp.toPx())
                    )
                    drawLine(
                        color = color,
                        start = Offset(-7.dp.toPx(), 23.dp.toPx()),
                        end = Offset(size.width - 10.dp.toPx(), 23.dp.toPx()),
                        strokeWidth = 2.dp.toPx()
                    )
                }
        ) {
            Column(modifier = Modifier.padding(start = 32.dp)) {
                Text(
                    text = stringResource(getExerciseTitle(TrainingType.PLANK)),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.padding(3.dp))
                Text(
                    text = buildAnnotatedString {
                        append(stringResource(R.string.last_train))
                        withStyle(SpanStyle(fontWeight = FontWeight.W700)) {
                            val string = uiState.plank.first?.let {
                                stringResource(R.string.x_times, it)
                            } ?: undefinedText
                            append(string)
                        }
                    },
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.padding(2.dp))
                Row {
                    Text(
                        text = buildAnnotatedString {
                            append(stringResource(R.string.progress))
                            withStyle(SpanStyle(fontWeight = FontWeight.W700)) {
                                val string = uiState.plank.second?.let {
                                    "$it%"
                                } ?: undefinedText
                                append(string)
                            }
                        },
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    uiState.plank.second?.let {
                        if (it == 0) return@let
                        Icon(
                            imageVector = ImageVector.vectorResource(
                                id = if (it > 0) R.drawable.arrow_up else R.drawable.arrow_down
                            ),
                            contentDescription = null,
                            tint = if (it > 0) Color.Green else Color.Red
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.fillMaxHeight(0.1f))

        Box(
            modifier = Modifier
                .padding(start = maxWidth.div(2.7f))
                .size(175.dp, 70.dp)
                .drawBehind {
                    drawCircle(
                        color = color,
                        radius = 4.dp.toPx(),
                        center = Offset(-32.dp.toPx(), -30.dp.toPx()),
                        style = Stroke(width = 2.dp.toPx())
                    )
                    drawLine(
                        color = color,
                        start = Offset(-30.dp.toPx(), -28.dp.toPx()),
                        end = Offset(25.dp.toPx(), 24.dp.toPx()),
                        strokeWidth = 2.dp.toPx()
                    )
                    drawLine(
                        color = color,
                        start = Offset(25.dp.toPx(), 24.dp.toPx()),
                        end = Offset(size.width, 24.dp.toPx()),
                        strokeWidth = 2.dp.toPx()
                    )
                }
        ) {
            Column(modifier = Modifier.padding(start = 32.dp)) {
                Text(
                    text = stringResource(getExerciseTitle(TrainingType.CRUNCH)),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.padding(3.dp))
                Text(
                    text = buildAnnotatedString {
                        append(stringResource(R.string.last_train))
                        withStyle(SpanStyle(fontWeight = FontWeight.W700)) {
                            val string = uiState.crunch.first?.let {
                                stringResource(R.string.x_times, it)
                            } ?: undefinedText
                            append(string)
                        }
                    },
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.padding(2.dp))
                Row {
                    Text(
                        text = buildAnnotatedString {
                            append(stringResource(R.string.progress))
                            withStyle(SpanStyle(fontWeight = FontWeight.W700)) {
                                val string = uiState.crunch.second?.let {
                                    "$it%"
                                } ?: undefinedText
                                append(string)
                            }
                        },
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    uiState.crunch.second?.let {
                        if (it == 0) return@let
                        Icon(
                            imageVector = ImageVector.vectorResource(
                                id = if (it > 0) R.drawable.arrow_up else R.drawable.arrow_down
                            ),
                            contentDescription = null,
                            tint = if (it > 0) Color.Green else Color.Red
                        )
                    }
                }
            }
        }
    }
}