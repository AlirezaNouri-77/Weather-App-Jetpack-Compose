package com.shermanrex.weatherapp.jetpack.weatherapp.retrofit

import com.shermanrex.weatherapp.jetpack.weatherapp.Constant.Constant
import com.shermanrex.weatherapp.jetpack.weatherapp.models.remote.weatherModels.CurrentWeatherModel
import com.shermanrex.weatherapp.jetpack.weatherapp.models.remote.searchModel.SearchCityApiModel
import com.shermanrex.weatherapp.jetpack.weatherapp.models.remote.weatherModels.SevenDayForecastModel
import com.shermanrex.weatherapp.jetpack.weatherapp.models.remote.weatherModels.ThreeHourWeatherModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {
    @GET("direct?")
    suspend fun getSearchApi(
        @Query("limit") limit: Int = 20,
        @Query("q") cityName: String,
        @Query("appid") Apikey: String = Constant.OPENWEATHER_API_KEY,
        ): Response<SearchCityApiModel>

    @GET("weather?")
    suspend fun getCurrentWeatherApi(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String,
        @Query("appid") key: String = Constant.OPENWEATHER_API_KEY,
        ): Response<CurrentWeatherModel>

    @GET("forecast/daily?")
    suspend fun getSevenDayWeatherApi(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String,
        @Query("key") key: String = Constant.WEATHERBIT_API_KEY,
        ): Response<SevenDayForecastModel>

    @GET("forecast?")
    suspend fun getThreeHourWeatherApi(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String,
        @Query("appid") Apikey: String = Constant.OPENWEATHER_API_KEY,
        ): Response<ThreeHourWeatherModel>

}