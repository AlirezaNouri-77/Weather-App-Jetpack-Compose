package com.shermanrex.weatherapp.jetpack.weatherapp.navigation.model

sealed class NavRoute(var route: String) {
  data object WeatherScreen : NavRoute("MainApp")
  data object SearchCityScreen : NavRoute("SearchScreenScreen")
}
