package com.shermanrex.weatherapp.jetpack.weatherapp.screenComponent.bottomsheet

import android.graphics.Color.CYAN
import android.graphics.Color.TRANSPARENT
import android.graphics.Color.YELLOW
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.shermanrex.weatherapp.jetpack.weatherapp.models.chart.ChartModel
import com.shermanrex.weatherapp.jetpack.weatherapp.models.remote.weatherModels.SevenDayForecastModel
import com.shermanrex.weatherapp.jetpack.weatherapp.screenComponent.chart.ChartChips
import com.shermanrex.weatherapp.jetpack.weatherapp.screenComponent.chart.WeatherChart
import com.shermanrex.weatherapp.jetpack.weatherapp.screenComponent.chart.WeatherChartEnum

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChartBottomSheet(
  onDismiss: () -> Unit,
  sheetState: SheetState,
  ForecastData: List<SevenDayForecastModel.Data>,
) {
    
    val weatherChartEnum = remember {
        mutableStateOf(WeatherChartEnum.AverageTemp)
    }

    val tempList: MutableList<ChartModel> = mutableListOf()
    val maxtempList: MutableList<ChartModel> = mutableListOf()
    val mintempList: MutableList<ChartModel> = mutableListOf()
    val rainList: MutableList<ChartModel> = mutableListOf()
    val uvindex: MutableList<ChartModel> = mutableListOf()

    for (i in 1 until ForecastData.size) {
        tempList.add(
            ChartModel(
                ForecastData[i].ts.toLong(),
                ForecastData[i].temp
            )
        )
        uvindex.add(
            ChartModel(
                ForecastData[i].ts.toLong(),
                ForecastData[i].uv
            )
        )
        maxtempList.add(
            ChartModel(
                ForecastData[i].ts.toLong(),
                ForecastData[i].maxTemp
            )
        )
        mintempList.add(
            ChartModel(
                ForecastData[i].ts.toLong(),
                ForecastData[i].minTemp
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
                        inputList = tempList,
                        chartColor = listOf(
                            Color(android.graphics.Color.GREEN).copy(alpha = 0.9f),
                            Color(android.graphics.Color.GREEN).copy(alpha = 0.1f)
                        ),
                        uppervalue = tempList.maxOfOrNull { it.value.plus(1) } ?: 0.0,
                        lowervalue = tempList.minOfOrNull { it.value } ?: 0.0
                    )
                }

                WeatherChartEnum.MinTemp -> {
                    WeatherChart(
                        inputList = mintempList,
                        chartColor = listOf(
                            Color(CYAN).copy(alpha = 0.9f),
                            Color(CYAN).copy(alpha = 0.1f),
                        ),
                        uppervalue = mintempList.maxOfOrNull { it.value.plus(1) } ?: 0.0,
                        lowervalue = mintempList.minOfOrNull { it.value } ?: 0.0
                    )
                }

                WeatherChartEnum.MaxTemp -> {
                    WeatherChart(
                        inputList = maxtempList,
                        chartColor = listOf(
                            Color(android.graphics.Color.RED).copy(alpha = 0.9f),
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
                            inputList = rainList,
                            chartColor = listOf(
                                Color(0xFF0197F6).copy(alpha = 0.9f),
                                Color(0xFF0197F6).copy(alpha = 0.1f),
                            ),
                            uppervalue = 100.0,
                            lowervalue = 0.0
                        )
                    }

                }

                WeatherChartEnum.UVindex -> {
                    WeatherChart(
                        inputList = uvindex,
                        chartColor = listOf(
                            Color(YELLOW).copy(alpha = 0.9f),
                            Color(YELLOW).copy(alpha = 0.1f),
                        ),
                        uppervalue = 11.0,
                        lowervalue = 0.0
                    )
                }

                else -> {}
            }
        }
    }
}


