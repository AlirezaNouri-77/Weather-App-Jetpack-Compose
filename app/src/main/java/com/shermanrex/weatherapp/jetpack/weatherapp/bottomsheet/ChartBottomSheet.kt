package com.shermanrex.weatherapp.jetpack.weatherapp.bottomsheet

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shermanrex.weatherapp.jetpack.weatherapp.models.ForecastData
import com.shermanrex.weatherapp.jetpack.weatherapp.models.WeatherChartModel
import com.shermanrex.weatherapp.jetpack.weatherapp.screenComponent.ChartChips
import com.shermanrex.weatherapp.jetpack.weatherapp.screenComponent.WeatherChart
import com.shermanrex.weatherapp.jetpack.weatherapp.screenComponent.WeatherChartEnum

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun ChartBottomSheet(
    onDismiss: () -> Unit,
    sheetState: SheetState,
    ForecastData: List<ForecastData>,
    modifier: Modifier = Modifier
) {

    val weatherChartEnum = remember {
        mutableStateOf(WeatherChartEnum.AverageTemp)
    }

    val tempList: MutableList<WeatherChartModel> = mutableListOf()
    val maxtempList: MutableList<WeatherChartModel> = mutableListOf()
    val mintempList: MutableList<WeatherChartModel> = mutableListOf()
    val rainList: MutableList<WeatherChartModel> = mutableListOf()

    for (i in 1 until ForecastData.size) {
        tempList.add(
            WeatherChartModel(
                ForecastData[i].ts.toLong(),
                ForecastData[i].temp
            )
        )
    }
    for (i in 1 until ForecastData.size) {
        maxtempList.add(
            WeatherChartModel(
                ForecastData[i].ts.toLong(),
                ForecastData[i].max_temp
            )
        )
    }
    for (i in 1 until ForecastData.size) {
        mintempList.add(
            WeatherChartModel(
                ForecastData[i].ts.toLong(),
                ForecastData[i].min_temp
            )
        )
    }

    for (i in 1 until ForecastData.size) {
        rainList.add(
            WeatherChartModel(
                ForecastData[i].ts.toLong(),
                ForecastData[i].pop.toDouble()
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

            AnimatedContent(targetState = weatherChartEnum.value) { item ->
                when (item) {
                    WeatherChartEnum.AverageTemp -> {
                        WeatherChart(tempList)
                    }

                    WeatherChartEnum.MinTemp -> {
                        WeatherChart(maxtempList)
                    }

                    WeatherChartEnum.MaxTemp -> {
                        WeatherChart(mintempList)
                    }

                    WeatherChartEnum.Rain -> {
                        val lower = rainList.minOfOrNull { it.value } ?: 0.0
                        val upper = rainList.maxOfOrNull { it.value } ?: 0.0
                        if (lower.plus(upper) == 0.0) {
                            Text(
                                text = " No chance for raining in forward 6 day :(",
                                color = MaterialTheme.colorScheme.onSecondary,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier
                                    .height(300.dp)
                                    .fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        } else {
                            WeatherChart(rainList)
                        }

                    }
                }
            }
        }
    }

}

