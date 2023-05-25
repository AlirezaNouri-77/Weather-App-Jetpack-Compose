package com.shermanrex.weatherapp.jetpack.weatherapp.screen.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.shermanrex.weatherapp.jetpack.weatherapp.models.CurrentWeatherModel
import com.shermanrex.weatherapp.jetpack.weatherapp.models.ResponseResultModel
import com.shermanrex.weatherapp.jetpack.weatherapp.models.SevenDayForecastModel
import com.shermanrex.weatherapp.jetpack.weatherapp.models.ThreeHourWeatherModel
import com.shermanrex.weatherapp.jetpack.weatherapp.models.WeatherResponseMapKey
import com.shermanrex.weatherapp.jetpack.weatherapp.screen.CollapseTopBar
import com.shermanrex.weatherapp.jetpack.weatherapp.screen.CurrentWeather
import com.shermanrex.weatherapp.jetpack.weatherapp.screen.EmptyWeather
import com.shermanrex.weatherapp.jetpack.weatherapp.screen.SevenDayForecast
import com.shermanrex.weatherapp.jetpack.weatherapp.screen.ThreehourForccast
import com.shermanrex.weatherapp.jetpack.weatherapp.screen.TopAppBar
import com.shermanrex.weatherapp.jetpack.weatherapp.viewModel.MyviewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainApp(navController: NavController , viewmodel: MyviewModel) {

    val respone by viewmodel.weatherApiResponse().collectAsState()

    val listState = rememberLazyListState()

    val iscollapse: Boolean by remember {
        derivedStateOf { listState.firstVisibleItemIndex > 1 }
    }

    Scaffold(
        topBar = {
            AnimatedVisibility(
                visible = iscollapse ,
                enter = fadeIn() ,
                exit = fadeOut()
            ) {
                CollapseTopBar()
            }
            AnimatedVisibility(
                visible = !iscollapse ,
                enter = fadeIn() ,
                exit = fadeOut()
            ) {
                TopAppBar(navController = navController)
            }
        } ,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            Modifier
                .background(
                    brush = Brush.verticalGradient(
                        listOf(
                            Color(0xFF004e92) ,
                            Color(0xFF000428)
                        )
                    )
                )
                .padding(it)
                .fillMaxSize()
        ) {

            when (respone) {
                is ResponseResultModel.Loading -> {
                    Shimmerloading()
                }

                is ResponseResultModel.Empty -> {
                    EmptyWeather(nav = navController)
                }

                is ResponseResultModel.Success -> {

                    val currentResponseData: CurrentWeatherModel =
                        (respone as ResponseResultModel.Success).data[WeatherResponseMapKey.CurrentForecast.toString()] as CurrentWeatherModel
                    val threeHourResponseData: ThreeHourWeatherModel =
                        (respone as ResponseResultModel.Success).data[WeatherResponseMapKey.ThreeHourforcast.toString()] as ThreeHourWeatherModel
                    val sevenDayResponseData: SevenDayForecastModel =
                        (respone as ResponseResultModel.Success).data[WeatherResponseMapKey.Sevendayforecast.toString()] as SevenDayForecastModel

                    LazyColumn(
                        Modifier
                            .fillMaxWidth() ,
                        verticalArrangement = Arrangement.Center ,
                        horizontalAlignment = Alignment.CenterHorizontally ,
                        state = listState
                    ) {

                        item {
                            TextDegreeSymbol(
                                currentDegree = viewmodel.getunitdataStore() ,
                                onclick = {
                                    viewmodel.updateDataStore(
                                        0.0 ,
                                        0.0 ,
                                        (if (viewmodel.getunitdataStore() == "metric") {
                                            "imperial"
                                        } else if (viewmodel.getunitdataStore() == "imperial") {
                                            "metric"
                                        } else {}) as String
                                    )
                                })
                        }
                        item {
                            CurrentWeather(data = currentResponseData)
                        }
                        item {
                            ThreehourForccast(data = threeHourResponseData)
                        }
                        item {
                            SevenDayForecast(data = sevenDayResponseData)
                        }
                    }
                }

                else -> {}
            }


        }
    }
}

@Composable
fun TextDegreeSymbol(currentDegree: String , onclick: () -> Unit) {
    Text(text = buildAnnotatedString {
        withStyle(
            SpanStyle(
                fontSize = 22.sp ,
                fontWeight = FontWeight.Bold ,
                color = if (ismetric(currentDegree)) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onPrimary.copy(
                    0.5f
                )
            ) ,
            block = {
                append("\u2103")
            }
        )
        withStyle(
            SpanStyle(
                fontSize = 18.sp ,
                fontWeight = FontWeight.Bold ,
                color = MaterialTheme.colorScheme.onPrimary
            ) ,
            block = {
                append(" / ")
            }
        )
        withStyle(
            SpanStyle(
                fontSize = 22.sp ,
                fontWeight = FontWeight.Bold ,
                color = if (ismetric(currentDegree)) MaterialTheme.colorScheme.onPrimary.copy(
                    0.5f
                ) else MaterialTheme.colorScheme.onPrimary
            ) ,
            block = {
                append("\u2109")
            }
        )
    } , Modifier
        .clickable {
            onclick()
        })
}

fun ismetric(string: String): Boolean {
    return string == "metric"
}