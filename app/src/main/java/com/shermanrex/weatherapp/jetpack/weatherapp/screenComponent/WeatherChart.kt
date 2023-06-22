package com.shermanrex.weatherapp.jetpack.weatherapp.screenComponent

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shermanrex.weatherapp.jetpack.weatherapp.models.ForecastData
import com.shermanrex.weatherapp.jetpack.weatherapp.models.WeatherChartModel
import com.shermanrex.weatherapp.jetpack.weatherapp.util.timeStampFormatter
import kotlin.math.roundToInt

@Composable
fun WeatherChart(
    data: List<WeatherChartModel> = emptyList()
) {

    val timeStampFormatter by lazy {
        timeStampFormatter()
    }

    val inputList: MutableList<WeatherChartModel> = data as MutableList<WeatherChartModel>
//    for (index in data.indices) {
//        inputList.add(
//            WeatherChartModel(
//                timeStampFormatter.timeStampFormatterSevenDay(data[index].ts.toLong()),
//                data[index].temp
//            ))
//    }

    val chartSpacer = 100f

    val uppervalue = remember(inputList) {
        inputList.maxOfOrNull { it.value }?.plus(1) ?: 0.0
    }

    val colorChart = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.6f)

    val lowervalue = remember(inputList) {
        inputList.minOfOrNull { it.value } ?: 0.0
    }

    val valueStep = remember(inputList) {
        (uppervalue - lowervalue) / 5f
    }

    val density = LocalDensity.current
    val textPaint = remember(density) {
        Paint().apply {
            color = colorChart.toArgb()
            textSize = density.run { 12.sp.toPx() }
        }
    }

    Box(modifier = Modifier.fillMaxWidth()) {

        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        ) {

            val spacerPerDate = (size.width - chartSpacer) / inputList.size
            val spacerPerValue = (size.height - chartSpacer) / 5f


            (inputList.indices).forEach { index ->
                drawContext.canvas.nativeCanvas.drawText(
                    inputList[index].date.toString(),
                    chartSpacer + (index * spacerPerDate),
                    size.height - 30,
                    textPaint
                )
            }


//            (0..4).forEach {
//                drawContext.canvas.nativeCanvas.drawLine(
//                    chartSpacer,
//                    (size.height - chartSpacer - (it * spacerPerValue)) - 10,
//                    size.width - 20,
//                    (size.height - chartSpacer - (it * spacerPerValue)) - 10,
//                    textPaint
//                )
//            }


            (0..4).forEach { index ->
                drawContext.canvas.nativeCanvas.drawText(
                    (lowervalue + index * valueStep).roundToInt().toString(),
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

                    val currentvaluerate = (currentValue - lowervalue) / (uppervalue - lowervalue)
                    val nextvaluerate = (nextValue - lowervalue) / (uppervalue - lowervalue)

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

            val fill = android.graphics.Path(strokePath.asAndroidPath()).asComposePath().apply {
                lineTo(lastx, size.height - chartSpacer)
                lineTo(chartSpacer, size.height - chartSpacer)
                close()
            }

            drawPath(
                fill,
                brush = Brush.verticalGradient(
                        colors = listOf(
                            colorChart,
                            androidx.compose.ui.graphics.Color.Transparent
                        ),
                    endY = size.height - chartSpacer
                ),
                style = Stroke(
                    width = 2.dp.toPx(),
                )
            )

        }
    }
}


//@Preview(showBackground = true)
//@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_UNDEFINED, showBackground = true)
//@Preview(name = "Full Preview", showSystemUi = true)
//@Composable
//fun previewMychart() {
//    WeatherAppTheme() {
//        Box(Modifier.fillMaxWidth()) {
//            WeatherChart(
//                inputList = listOf(
//                    WeatherChartModel("Tom", 34.0),
//                    WeatherChartModel("jul", 36.0),
//                    WeatherChartModel("mol", 38.0),
//                    WeatherChartModel("koa", 39.0),
//                    WeatherChartModel("cas", 40.0),
//                    WeatherChartModel("gfa", 37.0),
//                    WeatherChartModel("as", 33.0),
//                )
//            )
//        }
//    }
//}