package com.shermanrex.weatherapp.jetpack.weatherapp.repository

import android.util.Log
import com.shermanrex.weatherapp.jetpack.weatherapp.models.CurrentWeatherModel
import com.shermanrex.weatherapp.jetpack.weatherapp.models.ResponseResultModel
import com.shermanrex.weatherapp.jetpack.weatherapp.models.SevenDayForecastModel
import com.shermanrex.weatherapp.jetpack.weatherapp.models.ThreeHourWeatherModel
import com.shermanrex.weatherapp.jetpack.weatherapp.models.WeatherResponseMapKey
import com.shermanrex.weatherapp.jetpack.weatherapp.retrofit.RetrofitService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named


class WeatherRepository @Inject constructor(
    @Named("WeatherbitRetrofit") var weatherbitretrofit: RetrofitService,
    @Named("OpenWeatherRetrofit") var openweatherretrofit: RetrofitService,
) {

    var _WeatherReponse = MutableStateFlow<ResponseResultModel>(ResponseResultModel.Idle)
    var WeatherResponse: StateFlow<ResponseResultModel> = _WeatherReponse
    var WeatherResponseMap: MutableMap<String, Any> = mutableMapOf()

    suspend fun getCurrent(lat: Double, lon: Double, unit: String) {

        _WeatherReponse.value = ResponseResultModel.Loading

        CoroutineScope(Dispatchers.IO).launch {
            val response =
                openweatherretrofit.getCurrentWeatherApi(
                    lat,
                    lon,
                    unit
                )
            withContext(Dispatchers.IO) {
                if (response.isSuccessful) {
                    WeatherResponseMap.clear()
                    WeatherResponseMap[WeatherResponseMapKey.CurrentForecast.toString()] =
                        response.body() as CurrentWeatherModel
                    getSevenDayForecast(lat, lon, unit)
                }
            }
        }


    }

    fun getSevenDayForecast(lat: Double, lon: Double, unit: String) {

        CoroutineScope(Dispatchers.IO).launch {
            val response =
                weatherbitretrofit.getSevenDayWeatherApi(
                    lat,
                    lon,
                    unit
                )
            withContext(Dispatchers.IO) {
                if (response.body().toString().isEmpty()) {
                    _WeatherReponse.value = ResponseResultModel.Error("Nothing Found")
                } else if (response.isSuccessful) {
                    WeatherResponseMap[WeatherResponseMapKey.Sevendayforecast.toString()] =
                        response.body() as SevenDayForecastModel
                    getThreeHourForecast(lat, lon, unit)
                }
            }
        }

    }

    fun getThreeHourForecast(lat: Double, lon: Double, unit: String) {

        CoroutineScope(Dispatchers.IO).launch {
            val response =
                openweatherretrofit.getThreeHourWeatherApi(
                    lat,
                    lon,
                    units = unit
                )
            withContext(Dispatchers.IO) {
                if (response.isSuccessful) {
                    WeatherResponseMap[WeatherResponseMapKey.ThreeHourforcast.toString()] =
                        response.body() as ThreeHourWeatherModel
                    _WeatherReponse.value = ResponseResultModel.Success(WeatherResponseMap)
                } else {

                }
            }
        }
    }

    fun setWeatherResposneResult() {
        _WeatherReponse.value = ResponseResultModel.Empty
    }

}