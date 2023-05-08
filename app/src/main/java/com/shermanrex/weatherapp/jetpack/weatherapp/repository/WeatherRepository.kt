package com.shermanrex.weatherapp.jetpack.weatherapp.repository

import com.shermanrex.weatherapp.jetpack.weatherapp.models.CurrentWeatherModel
import com.shermanrex.weatherapp.jetpack.weatherapp.models.SevenDayForecastModel
import com.shermanrex.weatherapp.jetpack.weatherapp.models.WeatherResponseMapKey
import com.shermanrex.weatherapp.jetpack.weatherapp.models.ResponseResultModel
import com.shermanrex.weatherapp.jetpack.weatherapp.models.ThreeHourWeatherModel
import com.shermanrex.weatherapp.jetpack.weatherapp.retrofit.retrofitService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named


class WeatherRepository @Inject constructor(
    @Named("WeatherbitRetrofit") var weatherbit_retrofit: Retrofit ,
    @Named("3hourApiRetrofit") var openweather_retrofit: Retrofit
) {

    private var _WeatherReponse = MutableStateFlow<ResponseResultModel>(ResponseResultModel.Idle)
    var WeatherResponse: StateFlow<ResponseResultModel> = _WeatherReponse

    var WeatherResponseMap: MutableMap<String , Any> = mutableMapOf()

    fun getCurrent() {
        weatherbit_retrofit.create(retrofitService::class.java).getCurerntWeatherApi(
            35.72196 ,
            51.3347
        ).enqueue(object : Callback<CurrentWeatherModel> {
            override fun onResponse(
                call: Call<CurrentWeatherModel> ,
                response: Response<CurrentWeatherModel>
            ) {
                if (response.isSuccessful) {
                    WeatherResponseMap[WeatherResponseMapKey.CurrentForecast.toString()] =
                        response.body() as CurrentWeatherModel
                }
            }

            override fun onFailure(call: Call<CurrentWeatherModel> , t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun getSevenDayForecast() {
        weatherbit_retrofit.create(retrofitService::class.java).getSevenDayWeatherApi(
            35.72196 ,
            51.3347
        ).enqueue(object : Callback<SevenDayForecastModel> {
            override fun onResponse(
                call: Call<SevenDayForecastModel> ,
                response: Response<SevenDayForecastModel>
            ) {
                if (response.body().toString().isEmpty()) {
                    _WeatherReponse.value = ResponseResultModel.Error("Nothing Found")
                } else if (response.isSuccessful) {
                    WeatherResponseMap[WeatherResponseMapKey.Sevendayforecast.toString()] =
                        response.body() as SevenDayForecastModel

                }
            }

            override fun onFailure(call: Call<SevenDayForecastModel> , t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun getThreeHourForecast(){
        openweather_retrofit.create(retrofitService::class.java).getThreeHourWeatherApi(
            35.72196 ,
            51.3347
        ).enqueue(object :Callback<ThreeHourWeatherModel>{
            override fun onResponse(
                call: Call<ThreeHourWeatherModel> ,
                response: Response<ThreeHourWeatherModel>
            ) {
                WeatherResponseMap[WeatherResponseMapKey.ThreeHourforcast.toString()] = response.body() as ThreeHourWeatherModel
                _WeatherReponse.value = ResponseResultModel.Success(WeatherResponseMap)
            }

            override fun onFailure(call: Call<ThreeHourWeatherModel> , t: Throwable) {
                TODO("Not yet implemented")
            }


        })
    }

}