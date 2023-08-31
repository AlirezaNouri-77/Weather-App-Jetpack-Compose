package com.shermanrex.weatherapp.jetpack.weatherapp.models


data class WeatherResponseData(
    var threeHourWeatherData : ThreeHourWeatherModel,
    var currentWeatherData : CurrentWeatherModel,
    var sevenDayWeatherData : SevenDayForecastModel
)
