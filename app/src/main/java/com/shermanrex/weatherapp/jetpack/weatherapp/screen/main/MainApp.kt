package com.shermanrex.weatherapp.jetpack.weatherapp.screen.main

import android.Manifest
import android.content.Intent
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
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
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.shermanrex.weatherapp.jetpack.weatherapp.R
import com.shermanrex.weatherapp.jetpack.weatherapp.models.CurrentWeatherModel
import com.shermanrex.weatherapp.jetpack.weatherapp.models.ResponseResultModel
import com.shermanrex.weatherapp.jetpack.weatherapp.models.SevenDayForecastModel
import com.shermanrex.weatherapp.jetpack.weatherapp.models.ThreeHourWeatherModel
import com.shermanrex.weatherapp.jetpack.weatherapp.models.WeatherResponseMapKey
import com.shermanrex.weatherapp.jetpack.weatherapp.navigation.NavControllerModel
import com.shermanrex.weatherapp.jetpack.weatherapp.screenComponent.DetailGridItem
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

    val response = weatherViewModel.weatherApiResponse().collectAsStateWithLifecycle().value
    val responseerror =
        weatherViewModel.weatherApiResponseError().collectAsStateWithLifecycle().value

    var test1 = ""
    var test2 = ""

    val listState = rememberLazyListState()

    val scope = rememberCoroutineScope()

    val context = LocalContext.current

    var alreadyShowedPermission by rememberSaveable {
        mutableStateOf(false)
    }

    val iscollapse: Boolean by remember {
        derivedStateOf { listState.firstVisibleItemIndex > 0 }
    }

    var isApiResponseNotEmpty: Boolean by rememberSaveable {
        mutableStateOf(false)
    }

    var showPermission: Boolean by rememberSaveable {
        mutableStateOf(false)
    }

    if (showPermission) {
        Requestpermission(
            ongranted = { weatherViewModel.callWeatherRepositoryWhenAppLaunch() },
            notgranted = { NavControllerModel.SearchCityScreen.Route },
            ispermissionShowed = alreadyShowedPermission,
            PermissionShowed = { alreadyShowedPermission = true },
        )
    }

    Scaffold(
        topBar = {
            AnimatedVisibility(
                visible = iscollapse,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                CollapseTopBar(
                    cityName = test1,
                    Temp = test2
                )
            }
            AnimatedVisibility(
                visible = !iscollapse,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                TopAppBar(
                    clickLocationIcon = {
                        showPermission = true
                    }, clickSearchIcon = {
                        navController.navigate(NavControllerModel.SearchCityScreen.Route)
                    })
            }
        },
        modifier = Modifier.fillMaxSize()
    ) {

        Box(
            Modifier
                .background(
                    // color = MaterialTheme.colorScheme.background
                    brush = Brush.verticalGradient(
                        listOf(
                            Color(0xFF004e92),
                            //     Color(0xFF000428)
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
                        showPermission = true
                    }

                    is ResponseResultModel.Success -> {

                        isApiResponseNotEmpty = true

                        val currentResponseData: CurrentWeatherModel =
                            response.data[WeatherResponseMapKey.CurrentForecast.toString()] as CurrentWeatherModel
                        val threeHourResponseData: ThreeHourWeatherModel =
                            response.data[WeatherResponseMapKey.ThreeHourforcast.toString()] as ThreeHourWeatherModel
                        val sevenDayResponseData: SevenDayForecastModel =
                            response.data[WeatherResponseMapKey.Sevendayforecast.toString()] as SevenDayForecastModel

                        test1 = currentResponseData.name
                        test2 = currentResponseData.main.temp.roundToInt().toString()

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
                                        weatherViewModel.callWeatherRepositoryWhenAppLaunch()
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
                                    horizontalArrangement = Arrangement.Center,
                                    verticalArrangement = Arrangement.Center,
                                    content = {
                                        item {
                                            DetailGridItem(
                                                icon = R.drawable.icon_pressure,
                                                iconDescription = "Feels Like",
                                                value = currentResponseData.main.feels_like.roundToInt()
                                                    .toString()+"°",
                                            )
                                        }
                                        item {
                                            DetailGridItem(
                                                icon = R.drawable.icon_pressure,
                                                iconDescription = "Pressure",
                                                value = currentResponseData.main.pressure.toString(),
                                            )
                                        }
                                        item {
                                            DetailGridItem(
                                                icon = R.drawable.icon_cloud,
                                                iconDescription = "Cloud",
                                                value = currentResponseData.clouds.all.toString() + " %",
                                            )
                                        }
                                        item {
                                            DetailGridItem(
                                                icon = R.drawable.icon_visibility,
                                                iconDescription = "Visibility",
                                                value = currentResponseData.visibility.toString(),
                                            )
                                        }
                                        item {
                                            DetailGridItem(
                                                icon = R.drawable.icon_humidity,
                                                iconDescription = "Humidity",
                                                value = currentResponseData.main.humidity.toString() + " %",
                                            )
                                        }
                                        item {
                                            DetailGridItem(
                                                icon = R.drawable.icon_wind_speed,
                                                iconDescription = "Wind Speed",
                                                value = currentResponseData.wind.speed.roundToInt()
                                                    .toString(),
                                            )
                                        }
                                        item {
                                            DetailGridItem(
                                                icon = R.drawable.icon_winddirection,
                                                iconDescription = "Wind Direction",
                                                value = currentResponseData.wind.deg.toString(),
                                            )
                                        }
                                    })
                            }
                        }
                    }

                    else -> {}

                }

            when (responseerror) {
                is ResponseResultModel.NetWork -> {
                    DialogScreen(
                        onclickButton = {
                            weatherViewModel.callWeatherRepositoryWhenAppLaunch()
                        },
                        onclickdismiss = { weatherViewModel.weatherApiResponseErrorToidle()  },
                        icon = R.drawable.icon_nowifi,
                        onclickbuttonText = "Try Again",
                        text = "Please check your internet connection and try again",
                        showDismissButtun = isApiResponseNotEmpty
                    )
                }
                is ResponseResultModel.Error -> {
                    weatherViewModel.weatherApiResponseToidle()
                    DialogScreen(
                        onclickButton = {
                            weatherViewModel.callWeatherRepositoryWhenAppLaunch()
                        },
                        onclickdismiss = { weatherViewModel.weatherApiResponseErrorToidle()  },
                        icon = R.drawable.icon_warning,
                        onclickbuttonText = "Try Again",
                        text = "something is wrong please check your internet connection if not work try again other time",
                        showDismissButtun = false
                    )
                }
                is ResponseResultModel.LocationNotOn -> {
                    DialogScreen(
                        onclickButton = {
                            context.startActivities(arrayOf(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)))
                        },
                        onclickdismiss = { weatherViewModel.weatherApiResponseErrorToidle()  },
                        icon = R.drawable.icon_warning,
                        onclickbuttonText = "Go to setting",
                        text = "lcoation service is turn off on your device please turn on or use manual search for city",
                        showDismissButtun = isApiResponseNotEmpty
                    )
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
    ispermissionShowed: Boolean,
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
    if (!ispermissionShowed) {
        SideEffect {
            PermissionShowed.invoke()
            permissionstate.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
        }
    }
}