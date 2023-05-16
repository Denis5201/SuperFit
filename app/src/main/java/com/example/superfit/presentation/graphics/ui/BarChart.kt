package com.example.superfit.presentation.graphics.ui

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.superfit.Constants
import com.example.superfit.domain.model.Training
import com.example.superfit.domain.model.TrainingType
import kotlin.math.roundToInt

@Composable
fun BarChart(
    trainingList: List<Training>,
    type: TrainingType,
    modifier: Modifier
) {

    val rectColor = MaterialTheme.colorScheme.primary
    val visibleCount = 5
    var scrollOffset by remember { mutableStateOf(0f) }

    BoxWithConstraints(modifier = modifier) {
        val scrollState = ScrollableState {
            scrollOffset = if (it > 0) {
                (scrollOffset - it * visibleCount / maxWidth.value).coerceAtLeast(0f)
            } else {
                (scrollOffset - it * visibleCount / maxWidth.value).coerceAtMost((trainingList.size - 1).toFloat())
            }
            it
        }

        val visibleTraining by remember {
            derivedStateOf {
                trainingList.subList(
                    fromIndex = scrollOffset.roundToInt()
                        .coerceIn(0, maxOf(trainingList.size - visibleCount, 0)),
                    toIndex = (scrollOffset.roundToInt() + visibleCount)
                        .coerceAtMost(trainingList.size)
                )
            }
        }

        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .scrollable(scrollState, Orientation.Horizontal)
        ) {
            val offset = 20.dp.toPx()

            drawLine(
                color = Color.White,
                start = Offset(offset, 0f),
                end = Offset(offset, size.height - offset),
                strokeWidth = 1.dp.toPx()
            )
            drawLine(
                color = Color.White,
                start = Offset(offset, size.height - offset),
                end = Offset(size.width, size.height - offset),
                strokeWidth = 1.dp.toPx()
            )
            drawLine(
                color = Color.White,
                start = Offset(size.width, size.height - offset),
                end = Offset(size.width, 0f),
                strokeWidth = 1.dp.toPx()
            )
            drawLine(
                color = Color.White,
                start = Offset(size.width, 0f),
                end = Offset(offset, 0f),
                strokeWidth = 1.dp.toPx()
            )

            val repeatMax = trainingList.maxBy { it.repeatCount }.repeatCount
            val maxValue = if (type == TrainingType.RUNNING) repeatMax + 100 else repeatMax + 10
            val minValue = 0

            for (i in 1..3) {
                drawContext.canvas.nativeCanvas.drawText(
                    "${maxValue / 4 * i}",
                    0f, (size.height - offset ) / 4 * (4 - i), Paint().apply {
                        textSize = 10.sp.toPx()
                        color = Color.White.toArgb()
                    }
                )
                drawLine(
                    color = Color.White,
                    start = Offset(offset, (size.height - offset ) / 4 * (4 - i)),
                    end = Offset(size.width, (size.height - offset ) / 4 * (4 - i)),
                    strokeWidth = 1.dp.toPx()
                )
            }

            val pixelByValue = (size.height - offset) / (maxValue - minValue)
            val x = (size.width - offset) / minOf(visibleCount, visibleTraining.size)
            val lenRect = x - 2 * 6.dp.toPx()

            visibleTraining.forEachIndexed { index, training ->
                val offsetX = offset + x * index + 6.dp.toPx()
                drawLine(
                    color = Color.White,
                    start = Offset(offsetX + lenRect / 2, 0f),
                    end = Offset(offsetX + lenRect / 2, size.height - offset / 1.5f),
                    strokeWidth = 1.dp.toPx()
                )
                drawRect(
                    color = rectColor,
                    topLeft = Offset(offsetX,pixelByValue * (maxValue - training.repeatCount)),
                    size = Size(
                        lenRect,
                        (size.height - offset) - pixelByValue * (maxValue - training.repeatCount)
                    )
                )

                if (index == 0 || index == visibleTraining.size - 1) {
                    drawContext.canvas.nativeCanvas.drawText(
                        training.date.format(Constants.DATE_FORMAT),
                        offsetX + lenRect / 2 - offset - 4.dp.toPx(), size.height,
                        Paint().apply {
                            textSize = 10.sp.toPx()
                            color = Color.White.toArgb()
                        }
                    )
                }
            }
        }
    }
}