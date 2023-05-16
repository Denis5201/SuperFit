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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.superfit.Constants
import com.example.superfit.presentation.graphics.models.WeightParam
import kotlin.math.roundToInt

@Composable
fun LineChart(weightList: List<WeightParam>, modifier: Modifier) {

    val lineColor = MaterialTheme.colorScheme.primary
    val visibleCount = 5
    var scrollOffset by remember { mutableStateOf(0f) }

    BoxWithConstraints(modifier = modifier) {
        val scrollState = ScrollableState {
            scrollOffset = if (it > 0) {
                (scrollOffset - it * visibleCount / maxWidth.value).coerceAtLeast(0f)
            } else {
                (scrollOffset - it * visibleCount / maxWidth.value).coerceAtMost((weightList.size - 1).toFloat())
            }
            it
        }

        val visibleWeightParam by remember {
            derivedStateOf {
                weightList.subList(
                    fromIndex = scrollOffset.roundToInt()
                        .coerceIn(0, maxOf(weightList.size - visibleCount, 0)),
                    toIndex = (scrollOffset.roundToInt() + visibleCount)
                        .coerceAtMost(weightList.size)
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
                start = Offset(offset, offset),
                end = Offset(offset, size.height - offset),
                strokeWidth = 1.dp.toPx()
            )
            drawLine(
                color = Color.White,
                start = Offset(offset, size.height - offset),
                end = Offset(size.width - offset, size.height - offset),
                strokeWidth = 1.dp.toPx()
            )
            drawLine(
                color = Color.White,
                start = Offset(size.width - offset, size.height - offset),
                end = Offset(size.width - offset, offset),
                strokeWidth = 1.dp.toPx()
            )
            drawLine(
                color = Color.White,
                start = Offset(size.width - offset, offset),
                end = Offset(offset, offset),
                strokeWidth = 1.dp.toPx()
            )

            val maxWeight = weightList.maxBy { it.weight }.weight + 5
            val minWeight = weightList.minBy { it.weight }.weight - 5
            val middleWeight = (maxWeight + minWeight) / 2

            val pixelByWeight = (size.height - 2 * offset) / (maxWeight - minWeight)
            val widthWithoutOffset = (size.width - 2 * offset)

            drawContext.canvas.nativeCanvas.drawText(
                "$maxWeight",
                0f, offset, Paint().apply {
                    textSize = 10.sp.toPx()
                    color = Color.White.toArgb()
                }
            )
            drawContext.canvas.nativeCanvas.drawText(
                "$middleWeight",
                0f, size.height / 2, Paint().apply {
                    textSize = 10.sp.toPx()
                    color = Color.White.toArgb()
                }
            )
            drawLine(
                color = Color.White,
                start = Offset(offset, size.height / 2),
                end = Offset(size.width - offset, size.height / 2),
                strokeWidth = 1.dp.toPx()
            )

            visibleWeightParam.forEachIndexed { index, weightParam ->
                if (index == 0) {
                    drawCircle(
                        lineColor,
                        5.dp.toPx(),
                        Offset(
                            offset,
                            offset + pixelByWeight * (maxWeight - weightParam.weight)
                        )
                    )
                    drawContext.canvas.nativeCanvas.drawText(
                        weightParam.date.format(Constants.DATE_FORMAT),
                        0f, size.height, Paint().apply {
                            textSize = 10.sp.toPx()
                            color = Color.White.toArgb()
                        }
                    )
                    return@forEachIndexed
                }

                val prevOffset = Offset(
                    offset + (widthWithoutOffset * (index - 1) / (minOf(
                        visibleCount,
                        visibleWeightParam.size
                    ) - 1)),
                    offset + pixelByWeight * (maxWeight - visibleWeightParam[index - 1].weight)
                )
                val currentOffset = Offset(
                    offset + (widthWithoutOffset * index / (minOf(
                        visibleCount,
                        visibleWeightParam.size
                    ) - 1)),
                    offset + pixelByWeight * (maxWeight - weightParam.weight)
                )
                drawLine(
                    color = lineColor,
                    start = prevOffset,
                    end = currentOffset,
                    strokeWidth = 2.dp.toPx()
                )
                if (index % 2 == 0 || index == visibleWeightParam.size - 1) {
                    drawLine(
                        color = Color.White,
                        start = Offset(currentOffset.x, offset),
                        end = Offset(currentOffset.x, size.height - offset),
                        strokeWidth = 1.dp.toPx()
                    )
                    drawContext.canvas.nativeCanvas.drawText(
                        weightParam.date.format(Constants.DATE_FORMAT),
                        currentOffset.x - offset - 4.dp.toPx(), size.height, Paint().apply {
                            textSize = 10.sp.toPx()
                            color = Color.White.toArgb()
                        }
                    )
                }
                drawCircle(
                    lineColor,
                    5.dp.toPx(),
                    currentOffset
                )
            }
        }
    }
}