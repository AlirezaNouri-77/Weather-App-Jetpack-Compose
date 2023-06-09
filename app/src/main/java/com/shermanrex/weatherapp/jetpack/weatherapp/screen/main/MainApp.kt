package com.shermanrex.weatherapp.jetpack.weatherapp.screen.main

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.shermanrex.weatherapp.jetpack.weatherapp.Constant.Constant.CELSIUS_SYMBOL
import com.shermanrex.weatherapp.jetpack.weatherapp.Constant.Constant.FAHRENHEIT_SYMBOL
import com.shermanrex.weatherapp.jetpack.weatherapp.R
import com.shermanrex.weatherapp.jetpack.weatherapp.models.CurrentWeatherModel
import com.shermanrex.weatherapp.jetpack.weatherapp.models.ResponseResultModel
import com.shermanrex.weatherapp.jetpack.weatherapp.models.SevenDayForecastModel
import com.shermanrex.weatherapp.jetpack.weatherapp.models.ThreeHourWeatherModel
import com.shermanrex.weatherapp.jetpack.weatherapp.models.WeatherResponseMapKey
import com.shermanrex.weatherapp.jetpack.weatherapp.navigation.NavControllerModel
import com.shermanrex.weatherapp.jetpack.weatherapp.screenComponent.BottomSheetGridLayout
import com.shermanrex.weatherapp.jetpack.weatherapp.screenComponent.CollapseTopBar
import com.shermanrex.weatherapp.jetpack.weatherapp.screenComponent.CurrentWeather
import com.shermanrex.weatherapp.jetpack.weatherapp.screenComponent.SevenDayForecast
import com.shermanrex.weatherapp.jetpack.weatherapp.screenComponent.ThreehourForccast
import com.shermanrex.weatherapp.jetpack.weatherapp.screenComponent.TopAppBar
import com.shermanrex.weatherapp.jetpack.weatherapp.viewModel.WeatherViewModel
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun MainApp(navController: NavController, weatherViewModel: WeatherViewModel) {

    val response = weatherViewModel.weatherApiResponse()
        .collectAsState(initial = ResponseResultModel.Idle).value

    val listState = rememberLazyListState()

    var scope = rememberCoroutineScope()

    var alreadyShowedPermission by rememberSaveable {
        mutableStateOf(false)
    }

    val iscollapse: Boolean by remember {
        derivedStateOf { listState.firstVisibleItemIndex > 1 }
    }

    Scaffold(
        topBar = {
            AnimatedVisibility(
                visible = iscollapse,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                CollapseTopBar()
            }
            AnimatedVisibility(
                visible = !iscollapse,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                TopAppBar(navController = navController)
            }
        },
        modifier = Modifier.fillMaxSize()
    ) { it ->
        Box(
            Modifier
                .background(
                    brush = Brush.verticalGradient(
                        listOf(
                            Color(0xFF004e92),
                            Color(0xFF000428)
                        )
                    )
                )
                .padding(it)
                .fillMaxSize()
        ) {

            when (response) {

                is ResponseResultModel.Loading -> {
                    Shimmerloading()
                }

                is ResponseResultModel.Empty -> {
                    Requestpermission(
                        ongranted = { weatherViewModel.callWeatherRepository() },
                        notgranted = {
                            navController.navigate(
                                NavControllerModel.SearchCityScreen.Route
                            )
                        }, isShowedPermission = alreadyShowedPermission, PermissionShowed = {
                            alreadyShowedPermission = true
                        })
                }

                is ResponseResultModel.Success -> {

                    val currentResponseData: CurrentWeatherModel =
                        response.data[WeatherResponseMapKey.CurrentForecast.toString()] as CurrentWeatherModel
                    val threeHourResponseData: ThreeHourWeatherModel =
                        response.data[WeatherResponseMapKey.ThreeHourforcast.toString()] as ThreeHourWeatherModel
                    val sevenDayResponseData: SevenDayForecastModel =
                        response.data[WeatherResponseMapKey.Sevendayforecast.toString()] as SevenDayForecastModel

                    LazyColumn(
                        Modifier
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        state = listState
                    ) {
                        item {
                            CurrentWeather(
                                currentResponseData,
                                weatherViewModel.getWeatherUnitDataStore(),
                                onClickDegree = { unit ->
                                    weatherViewModel.updateUnitDataStore(unit)
                                    weatherViewModel.callWeatherRepository()
                                },
                                onMoreDetail = {
                                    scope.launch {

                                        listState.animateScrollToItem(4)
                                    }
                                }
                            )
                        }
                        item {
                            ThreehourForccast(data = threeHourResponseData)
                        }
                        item {
                            SevenDayForecast(data = sevenDayResponseData)
                        }
                        item {
                            LazyVerticalGrid(columns = GridCells.Fixed(2),
                                Modifier.heightIn(max = 1000.dp),
                                contentPadding = PaddingValues(10.dp),
                                content = {
                                    item {
                                        val description =
                                            if (currentResponseData.main.temp.roundToInt() > currentResponseData.main.feels_like.roundToInt()) {
                                                "Wind make it cooler"
                                            } else if (currentResponseData.main.temp.roundToInt() == currentResponseData.main.feels_like.roundToInt()) {
                                                ""
                                            } else {
                                                ""
                                            }
                                        BottomSheetGridLayout(
                                            icon = R.drawable.icon_pressure,
                                            Headline = "Feels Like",
                                            Value = currentResponseData.main.feels_like.roundToInt()
                                                .toString(),
                                            Desc = description
                                        )
                                    }
                                    item {
                                        BottomSheetGridLayout(
                                            icon = R.drawable.icon_pressure,
                                            Headline = "Pressure",
                                            Value = currentResponseData.main.pressure.toString(),
                                        )
                                    }
                                    item {
                                        BottomSheetGridLayout(
                                            icon = R.drawable.icon_cloud,
                                            Headline = "Cloud",
                                            Value = currentResponseData.clouds.all.toString() + " %",
                                        )
                                    }
                                    item {
                                        BottomSheetGridLayout(
                                            icon = R.drawable.icon_visibility,
                                            Headline = "Visibility",
                                            Value = currentResponseData.visibility.toString(),
                                        )
                                    }
                                    item {
                                        BottomSheetGridLayout(
                                            icon = R.drawable.icon_humidity,
                                            Headline = "Humidity",
                                            Value = currentResponseData.main.humidity.toString() + " %",
                                        )
                                    }
                                    item {
                                        BottomSheetGridLayout(
                                            icon = R.drawable.icon_wind_speed,
                                            Headline = "Wind Speed",
                                            Value = currentResponseData.wind.speed.roundToInt()
                                                .toString(),
                                        )
                                    }
                                    item {
                                        BottomSheetGridLayout(
                                            icon = R.drawable.icon_winddirection,
                                            Headline = "Wind Direction",
                                            Value = currentResponseData.wind.deg.toString(),
                                        )
                                    }
                                })
                        }
                    }
                }

                else -> {}
            }
        }
    }
}


fun ismetric(string: String): Boolean {
    return string == "METRIC"
}


@Composable
fun Requestpermission(
    ongranted: () -> Unit,
    notgranted: () -> Unit,
    isShowedPermission: Boolean,
    PermissionShowed: () -> Unit
) {

    val permissionstate =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
            onResult = {
                if (it) {
                    ongranted.invoke()
                } else {
                    notgranted.invoke()
                }
            }
        )
    if (!isShowedPermission) {
        SideEffect {
            PermissionShowed.invoke()
            permissionstate.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
        }
    }
}