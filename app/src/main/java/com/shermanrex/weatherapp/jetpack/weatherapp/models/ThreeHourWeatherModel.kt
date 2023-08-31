package com.shermanrex.weatherapp.jetpack.weatherapp.models

data class ThreeHourWeatherModel(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<datalist>,
    val message: Int
)

data class City(
    val coord: Coord,
    val country: String,
    val id: Int,
    val name: String,
    val population: Int,
    val sunrise: Int,
    val sunset: Int,
    val timezone: Int
)

data class datalist (
    val clouds: Clouds ,
    val dt: Int ,
    val dt_txt: String ,
    val main: Main ,
    val pop: Double ,
    val rain: Rain ,
    val sys: Sys ,
    val visibility: Int ,
    val weather: List<threehourWeather> ,
    val wind: Wind
)

data class Coord(
    val lat: Double,
    val lon: Double
)

data class Clouds(
    val all: Int
)

data class Main(
    val feels_like: Double,
    val grnd_level: Int,
    val humidity: Int,
    val pressure: Int,
    val sea_level: Int,
    val temp: Double,
    val temp_kf: Double,
    val temp_max: Double,
    val temp_min: Double
)

data class Rain(
    val threeHour: Double
)

data class Sys(
    val pod: String
)

data class threehourWeather (
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)

data class Wind(
    val deg: Int,
    val gust: Double,
    val speed: Double
)