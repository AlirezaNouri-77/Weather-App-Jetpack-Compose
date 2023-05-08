package com.shermanrex.weatherapp.jetpack.weatherapp.retrofit

import androidx.compose.ui.unit.Dp
import com.shermanrex.weatherapp.jetpack.weatherapp.models.CurrentWeatherModel
import com.shermanrex.weatherapp.jetpack.weatherapp.models.SearchCityApiModel
import com.shermanrex.weatherapp.jetpack.weatherapp.models.SevenDayForecastModel
import com.shermanrex.weatherapp.jetpack.weatherapp.models.ThreeHourWeatherModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface retrofitService {

    @GET("direct?")
    fun getSearchApi(
        @Query("limit") limit:Int = 10 ,
        @Query("appid") Apikey:String = "0368bc9fc5528ea37021049634b33bbf",
        @Query("q") cityName:String
    ): Call<SearchCityApiModel>

    @GET("current?")
    fun getCurerntWeatherApi(
        @Query("lat") lat:Double,
        @Query("lon") lon:Double,
        @Query("key") key:String = "8a8ec0d5af5f4806ada672017c6d44b5"
    ):Call<CurrentWeatherModel>

    @GET(" forecast/daily?")
    fun getSevenDayWeatherApi(
        @Query("lat") lat:Double,
        @Query("lon") lon:Double,
        @Query("key") key:String = "8a8ec0d5af5f4806ada672017c6d44b5"
    ):Call<SevenDayForecastModel>

    @GET("forecast?")
    fun getThreeHourWeatherApi(
        @Query("lat") lat:Double,
        @Query("lon") lon:Double,
        @Query("appid") Apikey:String = "0368bc9fc5528ea37021049634b33bbf"
    ): Call<ThreeHourWeatherModel>

}