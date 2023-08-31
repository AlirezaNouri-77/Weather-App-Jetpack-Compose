package com.shermanrex.weatherapp.jetpack.weatherapp.screens

import android.Manifest
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.shermanrex.weatherapp.jetpack.weatherapp.models.SealedResponseResultModel
import com.shermanrex.weatherapp.jetpack.weatherapp.navigation.NavControllerModel
import com.shermanrex.weatherapp.jetpack.weatherapp.screenComponent.CollapseTopBar
import com.shermanrex.weatherapp.jetpack.weatherapp.screenComponent.CurrentWeather
import com.shermanrex.weatherapp.jetpack.weatherapp.screenComponent.CurrentWeatherDetial
import com.shermanrex.weatherapp.jetpack.weatherapp.screenComponent.SevenDayForecast
import com.shermanrex.weatherapp.jetpack.weatherapp.screenComponent.Shimmerloading
import com.shermanrex.weatherapp.jetpack.weatherapp.screenComponent.ThreehourForccast
import com.shermanrex.weatherapp.jetpack.weatherapp.screenComponent.TopAppBar
import com.shermanrex.weatherapp.jetpack.weatherapp.viewModel.WeatherViewModel
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun WeatherScreen(navController: NavController, weatherViewModel: WeatherViewModel) {

    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val response = weatherViewModel.weatherApiResponse().collectAsStateWithLifecycle().value
    val responseerror =
        weatherViewModel.weatherApiResponseError().collectAsStateWithLifecycle().value

    var cityName = ""
    var cityTemp = ""

    val listState = rememberLazyListState()

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
        RequestLocationPermission(
            ongranted = { weatherViewModel.callWeatherRepository() },
            notgranted = { navController.navigate(NavControllerModel.SearchCityScreen.route) },
            ispermissionShowed = alreadyShowedPermission,
            permissionShowed = { alreadyShowedPermission = true },
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
                    cityName = cityName,
                    Temp = cityTemp
                )
            }
            AnimatedVisibility(
                visible = !iscollapse,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                TopAppBar(
                    clickLocationIcon = {
                        if (weatherViewModel.checkLocationGranted()) {
                            showPermission = true
                        } else {
                            weatherViewModel.updateWeatherResponseError(SealedResponseResultModel.Location)
                        }
                    }, clickSearchIcon = {
                        navController.navigate(NavControllerModel.SearchCityScreen.route)
                    })
            }
        },
        modifier = Modifier.fillMaxSize()
    ) {
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
            //  AnimatedContent(targetState = response, label = "") { targetstate ->

            when (response) {
                SealedResponseResultModel.Loading -> {
                    Shimmerloading()
                }

                is SealedResponseResultModel.Success -> {

                    isApiResponseNotEmpty = true

//                    val currentResponseData = response.data.currentWeatherData
//                    val threeHourResponseData = response.data.threeHourWeatherData
//                    val sevenDayResponseData = response.data.sevenDayWeatherData

                    cityName = response.data.currentWeatherData.name
                    cityTemp = response.data.currentWeatherData.main.temp.roundToInt().toString()

                    LazyColumn(
                        Modifier
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        state = listState
                    ) {
                        item {
                            CurrentWeather(
                                response.data.currentWeatherData,
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
                            ThreehourForccast(data = response.data.threeHourWeatherData)
                        }
                        item {
                            SevenDayForecast(data = response.data.sevenDayWeatherData)
                        }
                        item {
                            CurrentWeatherDetial(data = response.data.currentWeatherData)
                        }
                    }
                }

                SealedResponseResultModel.Empty -> {
                    showPermission = true
                }

                else -> {}

            }


            when (responseerror) {
                is SealedResponseResultModel.NetWork -> {
                    DialogScreen(
                        dialogImage = R.drawable.icon_nowifi,
                        dialogMessage = "Please check your internet connection and Try again"
                    ) {
                        Button(onClick = { weatherViewModel.callWeatherRepository() }) {
                            Text(text = "Try Again")
                        }
                        Spacer(modifier = Modifier.width(5.dp))
                        if (isApiResponseNotEmpty) {
                            Button(onClick = {
                                weatherViewModel.updateWeatherResponseError(
                                    SealedResponseResultModel.Idle
                                )
                            }) {
                                Text(text = "close")
                            }
                        }
                    }
                }

                is SealedResponseResultModel.Error -> {
                    DialogScreen(
                        dialogImage = R.drawable.icon_warning,
                        dialogMessage = "something goes wrong, please check your internet connection also it could be slow connection if that not maybe servers are down. Try again next time."
                    ) {
                        Button(onClick = { weatherViewModel.callWeatherRepository() }) {
                            Text(text = "Try Again")
                        }
                        Spacer(modifier = Modifier.width(5.dp))
                        if (isApiResponseNotEmpty) {
                            Button(onClick = {
                                weatherViewModel.updateWeatherResponseError(
                                    SealedResponseResultModel.Idle
                                )
                            }) {
                                Text(text = "close")
                            }
                        }
                    }
                }

                is SealedResponseResultModel.LocationNotOn -> {
                    DialogScreen(
                        dialogImage = R.drawable.icon_warning,
                        dialogMessage = "Location service turned off on your device. Please turn it on and try Again."
                    ) {
                        Button(onClick = { weatherViewModel.callWeatherRepository() }) {
                            Text(text = "Try Again")
                        }
                        Spacer(modifier = Modifier.width(5.dp))
                        if (isApiResponseNotEmpty) {
                            Button(onClick = {
                                weatherViewModel.updateWeatherResponseError(
                                    SealedResponseResultModel.Idle
                                )
                            }) {
                                Text(text = "close")
                            }
                        }
                    }
                }

                is SealedResponseResultModel.Location -> {
                    DialogScreen(
                        dialogImage = R.drawable.icon_warning,
                        dialogMessage = "Location permission not granted for this app, Please grant that for use location service."
                    ) {
                        Button(onClick = {
                            val intent =
                                Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                            val uri = Uri.fromParts("package", context.packageName, null)
                            intent.data = uri
                            context.startActivities(arrayOf(intent))
                        }) {
                            Text(text = "Settings")
                        }
                        Spacer(modifier = Modifier.width(5.dp))
                        Button(onClick = { weatherViewModel.callWeatherRepository() }) {
                            Text(text = "Try again")
                        }
                        Spacer(modifier = Modifier.width(5.dp))
                        if (isApiResponseNotEmpty) {
                            Button(onClick = {
                                weatherViewModel.updateWeatherResponseError(
                                    SealedResponseResultModel.Idle
                                )
                            }) {
                                Text(text = "close")
                            }
                        }
                    }
                }


                else -> {}
            }
        }
    }
}

@Composable
fun RequestLocationPermission(
    ongranted: () -> Unit,
    notgranted: () -> Unit,
    ispermissionShowed: Boolean,
    permissionShowed: () -> Unit
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
            permissionShowed.invoke()
            permissionstate.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
        }
    }
}