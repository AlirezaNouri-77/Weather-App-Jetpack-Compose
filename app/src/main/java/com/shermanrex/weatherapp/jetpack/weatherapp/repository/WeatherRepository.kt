package com.shermanrex.weatherapp.jetpack.weatherapp.repository

import android.util.Log
import com.shermanrex.weatherapp.jetpack.weatherapp.models.CurrentWeatherModel
import com.shermanrex.weatherapp.jetpack.weatherapp.models.ResponseResultModel
import com.shermanrex.weatherapp.jetpack.weatherapp.models.SevenDayForecastModel
import com.shermanrex.weatherapp.jetpack.weatherapp.models.ThreeHourWeatherModel
import com.shermanrex.weatherapp.jetpack.weatherapp.models.WeatherResponseMapKey
import com.shermanrex.weatherapp.jetpack.weatherapp.retrofit.RetrofitService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named


class WeatherRepository @Inject constructor(
    @Named("WeatherbitRetrofit") var weatherbit_retrofit: Retrofit ,
    @Named("OpenWeatherRetrofit") var openweatherretrofit: Retrofit ,
) {

    var _WeatherReponse = MutableStateFlow<ResponseResultModel>(ResponseResultModel.Idle)
    var WeatherResponse: StateFlow<ResponseResultModel> = _WeatherReponse
    var WeatherResponseMap: MutableMap<String , Any> = mutableMapOf()

    suspend fun getCurrent(lat: Double , lon: Double , unit:String) = withContext(Dispatchers.IO) {

        WeatherResponseMap.clear()
        _WeatherReponse.value = ResponseResultModel.Loading

        val response = openweatherretrofit.create(RetrofitService::class.java).getCurrentWeatherApi(
            lat ,
            lon,
            units = unit
        ).execute()
        if (response.isSuccessful) {
            WeatherResponseMap[WeatherResponseMapKey.CurrentForecast.toString()] =
                response.body() as CurrentWeatherModel
            getSevenDayForecast(lat , lon , unit)
        }else if (!response.isSuccessful){


        }

//        openweatherretrofit.create(RetrofitService::class.java).getCurrentWeatherApi(
//            35.72196 ,
//            51.3347
//        ).enqueue(object : Callback<CurrentWeatherModel> {
//            override fun onResponse(
//                call: Call<CurrentWeatherModel> ,
//                response: Response<CurrentWeatherModel>
//            ) {
//                Log.d("TAG" , "onResponse: " + call.request().url())
//                if (response.isSuccessful) {
//                    WeatherResponseMap[WeatherResponseMapKey.CurrentForecast.toString()] =
//                        response.body() as CurrentWeatherModel
//                    getSevenDayForecast()
//                }
//            }
//
//            override fun onFailure(call: Call<CurrentWeatherModel> , t: Throwable) {
//
//            }
//
//        })
    }

    fun getSevenDayForecast(lat: Double , lon: Double, unit:String) {
        weatherbit_retrofit.create(RetrofitService::class.java).getSevenDayWeatherApi(
            lat ,
            lon ,
            units = unit
        ).enqueue(object : Callback<SevenDayForecastModel> {
            override fun onResponse(
                call: Call<SevenDayForecastModel> ,
                response: Response<SevenDayForecastModel>
            ) {
                Log.d("TAG" , "onResponse: " + call.request().url())
                if (response.body().toString().isEmpty()) {
                    _WeatherReponse.value = ResponseResultModel.Error("Nothing Found")
                } else if (response.isSuccessful) {
                    WeatherResponseMap[WeatherResponseMapKey.Sevendayforecast.toString()] =
                        response.body() as SevenDayForecastModel
                    getThreeHourForecast(lat , lon , unit)
                }
            }

            override fun onFailure(call: Call<SevenDayForecastModel> , t: Throwable) {
                Log.d("TAG" , "onFailure: " + t.message)
            }

        })
    }

    fun getThreeHourForecast(lat: Double , lon: Double, unit:String) {
        openweatherretrofit.create(RetrofitService::class.java).getThreeHourWeatherApi(
            lat ,
            lon ,
            units = unit
        ).enqueue(object : Callback<ThreeHourWeatherModel> {
            override fun onResponse(
                call: Call<ThreeHourWeatherModel> ,
                response: Response<ThreeHourWeatherModel>
            ) {
                WeatherResponseMap[WeatherResponseMapKey.ThreeHourforcast.toString()] =
                    response.body() as ThreeHourWeatherModel
                _WeatherReponse.value = ResponseResultModel.Success(WeatherResponseMap)

            }

            override fun onFailure(call: Call<ThreeHourWeatherModel> , t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }


}