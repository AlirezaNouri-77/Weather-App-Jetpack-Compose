package com.shermanrex.weatherapp.jetpack.weatherapp.bottomsheet

import android.graphics.Color.BLUE
import android.graphics.Color.CYAN
import android.graphics.Color.TRANSPARENT
import android.graphics.Color.YELLOW
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shermanrex.weatherapp.jetpack.weatherapp.R
import com.shermanrex.weatherapp.jetpack.weatherapp.models.ForecastData
import com.shermanrex.weatherapp.jetpack.weatherapp.models.WeatherChartModel
import com.shermanrex.weatherapp.jetpack.weatherapp.screenComponent.ChartChips
import com.shermanrex.weatherapp.jetpack.weatherapp.screenComponent.WeatherChart
import com.shermanrex.weatherapp.jetpack.weatherapp.screenComponent.WeatherChartEnum

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChartBottomSheet(
    onDismiss: () -> Unit,
    sheetState: SheetState,
    ForecastData: List<ForecastData>,
) {

    var colorAlpha = 0.9f

    val weatherChartEnum = remember {
        mutableStateOf(WeatherChartEnum.AverageTemp)
    }

    val tempList: MutableList<WeatherChartModel> = mutableListOf()
    val maxtempList: MutableList<WeatherChartModel> = mutableListOf()
    val mintempList: MutableList<WeatherChartModel> = mutableListOf()
    val rainList: MutableList<WeatherChartModel> = mutableListOf()
    val uvindex: MutableList<WeatherChartModel> = mutableListOf()

    for (i in 1 until ForecastData.size) {
        tempList.add(
            WeatherChartModel(
                ForecastData[i].ts.toLong(),
                ForecastData[i].temp
            )
        )
        uvindex.add(
            WeatherChartModel(
                ForecastData[i].ts.toLong(),
                ForecastData[i].uv
            )
        )
        maxtempList.add(
            WeatherChartModel(
                ForecastData[i].ts.toLong(),
                ForecastData[i].max_temp
            )
        )
        mintempList.add(
            WeatherChartModel(
                ForecastData[i].ts.toLong(),
                ForecastData[i].min_temp
            )
        )
    }


    ModalBottomSheet(
        onDismissRequest = { onDismiss.invoke() },
        shape = RoundedCornerShape(topEnd = 15.dp, topStart = 15.dp),
        sheetState = sheetState
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(10.dp)
        ) {
            ChartChips(
                onClick = {
                    weatherChartEnum.value = it
                },
                modifier = Modifier.padding(bottom = 15.dp)
            )

            when (weatherChartEnum.value) {
                WeatherChartEnum.AverageTemp -> {
                    WeatherChart(
                        datalist = tempList,
                        chartColor = listOf(
                            Color(android.graphics.Color.GREEN).copy(alpha = colorAlpha),
                            Color(TRANSPARENT)
                        ),
                        uppervalue = tempList.maxOfOrNull { it.value.plus(1) } ?: 0.0,
                        lowervalue = tempList.minOfOrNull { it.value } ?: 0.0
                    )
                }

                WeatherChartEnum.MinTemp -> {
                    WeatherChart(
                        datalist = mintempList,
                        chartColor = listOf(
                            Color(CYAN).copy(alpha = 0.6f),
                            Color(TRANSPARENT)
                        ),
                        uppervalue = mintempList.maxOfOrNull { it.value.plus(1) } ?: 0.0,
                        lowervalue = mintempList.minOfOrNull { it.value } ?: 0.0
                    )
                }

                WeatherChartEnum.MaxTemp -> {
                    WeatherChart(
                        datalist = maxtempList,
                        chartColor = listOf(
                            Color(android.graphics.Color.RED).copy(alpha = colorAlpha),
                            Color(TRANSPARENT)
                        ),
                        uppervalue = maxtempList.maxOfOrNull { it.value.plus(1) } ?: 0.0,
                        lowervalue = maxtempList.minOfOrNull { it.value } ?: 0.0
                    )
                }

                WeatherChartEnum.Rain -> {
                    val lower = rainList.minOfOrNull { it.value } ?: 0.0
                    val upper = rainList.maxOfOrNull { it.value } ?: 0.0
                    if (lower.plus(upper) == 0.0) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.height(300.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.icon_nochancerain),
                                contentDescription = null,
                                Modifier
                                    .size(70.dp)
                                    .padding(10.dp),
                                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary)
                            )
                            Text(
                                text = " No chance for raining in forward 6 day :(",
                                color = MaterialTheme.colorScheme.onSecondary,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                textAlign = TextAlign.Center
                            )
                        }
                    } else {
                        WeatherChart(
                            datalist = rainList,
                            chartColor = listOf(
                                Color(0xFF0197F6).copy(alpha = colorAlpha),
                                Color(TRANSPARENT)
                            ),
                            uppervalue = 100.0,
                            lowervalue = 0.0
                        )
                    }

                }

                WeatherChartEnum.UVindex -> {
                    WeatherChart(
                        datalist = uvindex,
                        chartColor = listOf(
                            Color(YELLOW).copy(alpha = colorAlpha),
                            Color(TRANSPARENT)
                        ),
                        uppervalue = 11.0,
                        lowervalue = 0.0
                    )
                }

                else -> {}
            }
            //}
        }
    }
}


