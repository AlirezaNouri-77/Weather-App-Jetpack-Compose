package com.shermanrex.weatherapp.jetpack.weatherapp.util

import com.shermanrex.weatherapp.jetpack.weatherapp.Model.WeatherIconModel

class WeatherIconFinder {

    private val weatherIconModel by lazy {
        WeatherIconModel()
    }

    fun getIcon(Code: Int , isNight: Boolean): Int {
        var icon = 0
        weatherIconModel.IconList.forEach {
            if (it.Code == Code) {
                icon = if (!isNight) {
                    it.IconNight
                } else {
                    it.IconDay
                }
            }
        }
        return icon
    }
}