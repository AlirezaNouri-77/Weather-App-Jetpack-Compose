package com.shermanrex.weatherapp.jetpack.weatherapp.screenComponent

import android.graphics.Paint
import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shermanrex.weatherapp.jetpack.weatherapp.models.WeatherChartModel
import com.shermanrex.weatherapp.jetpack.weatherapp.util.timeStampFormatter
import kotlin.math.roundToInt

@Composable
fun WeatherChart(
    datalist: List<WeatherChartModel> = emptyList(),
    uppervalue: Double,
    lowervalue: Double,
    chartColor: List<Color> = emptyList()
) {

    val timeStampFormatter by lazy {
        timeStampFormatter()
    }

    val animateState = remember {
        Animatable(0f)
    }

    LaunchedEffect(key1 = datalist, block = {
        animateState.animateTo(1f, animationSpec = tween(2000 , 60) )
    })

    val inputList: MutableList<WeatherChartModel> = datalist as MutableList<WeatherChartModel>

    val chartSpacer = 100f

    val textColor = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.9f)

    val valueStep = remember(inputList) {
        val diff = (uppervalue - lowervalue)
        if (diff < 5) {
            0.5
        } else {
            diff / 5f
        }
    }

    val density = LocalDensity.current
    val textPaint = remember(density) {
        Paint().apply {
            color = textColor.toArgb()
            textSize = density.run { 12.sp.toPx() }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        ) {

            val spacerPerDate = (size.width - chartSpacer) / inputList.size
            val spacerPerValue = (size.height - chartSpacer) / 6f

            (inputList.indices).forEach { index ->
                drawContext.canvas.nativeCanvas.drawText(
                    timeStampFormatter.timeStampFormatterChart(inputList[index].date),
                    chartSpacer + (index * spacerPerDate),
                    size.height - 30,
                    textPaint
                )
            }

            (0..5).forEach { index ->
                drawContext.canvas.nativeCanvas.drawText(
                    (lowervalue + index * valueStep).toString(),
                    35f,
                    size.height - chartSpacer - (index * spacerPerValue),
                    textPaint
                )
            }

            var lastx = 0f
            val strokePath = Path().apply {

                (inputList.indices).forEach { index ->

                    val currentValue = inputList[index].value
                    val nextValue = inputList.getOrNull(index + 1)?.value ?: inputList.last().value

                    val currentvaluerate =
                        (currentValue - lowervalue) / (uppervalue.plus(1) - lowervalue)
                    val nextvaluerate = (nextValue - lowervalue) / (uppervalue.plus(1) - lowervalue)

                    val x1 = chartSpacer + (index * spacerPerDate)
                    val y1 = size.height - chartSpacer - (currentvaluerate * size.height)

                    val x2 = chartSpacer + (index + 1) * spacerPerDate
                    val y2 = size.height - chartSpacer - (nextvaluerate * size.height)

                    if (index == 0) {
                        moveTo(x1, y1.toFloat())
                    }

                    lastx = (x1 + x2) / 2f

                    quadraticBezierTo(
                        x1 = x1,
                        y1 = y1.toFloat(),
                        x2 = (x1 + x2) / 2f,
                        y2 = ((y1 + y2) / 2f).toFloat()
                    )
                }
            }

            clipRect(right = size.width * animateState.value) {
                val fill = android.graphics.Path(strokePath.asAndroidPath()).asComposePath().apply {
                    lineTo(lastx, size.height - chartSpacer)
                    lineTo(chartSpacer, size.height - chartSpacer)
                    close()
                }

                drawPath(
                    fill,
                    brush = Brush.verticalGradient(
                        colors = chartColor,
                        endY = size.height - chartSpacer
                    ),
                )
            }
        }
    }
}
