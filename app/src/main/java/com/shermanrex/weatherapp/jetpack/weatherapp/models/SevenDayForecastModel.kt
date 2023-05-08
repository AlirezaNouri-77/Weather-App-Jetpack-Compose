package com.shermanrex.weatherapp.jetpack.weatherapp.models

data class SevenDayForecastModel(
    val city_name: String,
    val country_code: String,
    val `data`: List<ForecastData>,
    val lat: Double,
    val lon: Double,
    val state_code: String,
    val timezone: String
)

data class ForecastData(
    val app_max_temp: Double,
    val app_min_temp: Double,
    val clouds: Int,
    val clouds_hi: Int,
    val clouds_low: Int,
    val clouds_mid: Int,
    val datetime: String,
    val dewpt: Double,
    val high_temp: Double,
    val low_temp: Double,
    val max_dhi: Any,
    val max_temp: Double,
    val min_temp: Double,
    val moon_phase: Double,
    val moon_phase_lunation: Double,
    val moonrise_ts: Int,
    val moonset_ts: Int,
    val ozone: Double,
    val pop: Int,
    val precip: Int,
    val pres: Double,
    val rh: Int,
    val slp: Double,
    val snow: Int,
    val snow_depth: Int,
    val sunrise_ts: Int,
    val sunset_ts: Int,
    val temp: Double,
    val ts: Int,
    val uv: Double,
    val valid_date: String,
    val vis: Double,
    val weather: ForecastWeather,
    val wind_cdir: String,
    val wind_cdir_full: String,
    val wind_dir: Int,
    val wind_gust_spd: Double,
    val wind_spd: Double
)

data class ForecastWeather(
    val code: Int,
    val description: String,
    val icon: String
)