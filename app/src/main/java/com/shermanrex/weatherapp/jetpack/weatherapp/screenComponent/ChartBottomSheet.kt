package com.shermanrex.weatherapp.jetpack.weatherapp.screenComponent

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.shermanrex.weatherapp.jetpack.weatherapp.models.ForecastData
import com.shermanrex.weatherapp.jetpack.weatherapp.models.WeatherChartModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun ChartBottomSheet(
    onDismiss: () -> Unit,
    sheetState: SheetState,
    ForecastData: List<ForecastData>,
){

    val weatherChartEnum = remember {
        mutableStateOf(WeatherChartEnum.AverageTemp)
    }

    var temp:MutableList<WeatherChartModel> = mutableListOf()
    var maxtemp:MutableList<WeatherChartModel> = mutableListOf()
    var mintemp:MutableList<WeatherChartModel> = mutableListOf()
    var rain:MutableList<WeatherChartModel> = mutableListOf()

    (ForecastData.indices).forEach {
        temp.add(
            WeatherChartModel(
                ForecastData[it].ts.toLong(),
                ForecastData[it].temp
            )
        )
    }
    (ForecastData.indices).forEach {
        maxtemp.add(
            WeatherChartModel(
                ForecastData[it].ts.toLong(),
                ForecastData[it].max_temp
            )
        )
    }
    (ForecastData.indices).forEach {
        mintemp.add(
            WeatherChartModel(
                ForecastData[it].ts.toLong(),
                ForecastData[it].min_temp
            )
        )
    }

    (ForecastData.indices).forEach {
        rain.add(
            WeatherChartModel(
                ForecastData[it].ts.toLong(),
                ForecastData[it].precip)
        )
    }

    ModalBottomSheet(
        onDismissRequest = { onDismiss.invoke() },
        shape = RoundedCornerShape(topEnd = 15.dp, topStart = 15.dp),
        sheetState = sheetState
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ChartChips(onClick = {
                weatherChartEnum.value = it
            })

            AnimatedContent(targetState = weatherChartEnum.value) {
                when (it) {
                    WeatherChartEnum.AverageTemp ->{
                        WeatherChart(temp)
                    }
                    WeatherChartEnum.MinTemp ->{
                        WeatherChart(maxtemp)
                    }
                    WeatherChartEnum.MaxTemp->{
                        WeatherChart(mintemp)
                    }
                    WeatherChartEnum.Rain->{
                        WeatherChart(rain)
                    }
                }
            }

        }
    }

}
