package com.shermanrex.weatherapp.jetpack.weatherapp.models.remote

import com.shermanrex.weatherapp.jetpack.weatherapp.models.remote.weatherModels.CurrentWeatherModel
import com.shermanrex.weatherapp.jetpack.weatherapp.models.remote.weatherModels.SevenDayForecastModel
import com.shermanrex.weatherapp.jetpack.weatherapp.models.remote.weatherModels.ThreeHourWeatherModel

sealed interface ResponseModel {
  data object Idle : ResponseModel
  data object Loading : ResponseModel
  data class Success<T>(val data: T) : ResponseModel
  data class Error(val error: String) : ResponseModel
  data object UnAvailableNetwork : ResponseModel
  data object UnAvailableGpsService : ResponseModel
}

data class WeatherResponseData(
  var threeHourWeatherData: ThreeHourWeatherModel? = null,
  var currentWeatherData: CurrentWeatherModel? = null,
  var sevenDayWeatherData: SevenDayForecastModel? = null,
)