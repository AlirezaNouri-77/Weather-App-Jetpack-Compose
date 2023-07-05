package com.shermanrex.weatherapp.jetpack.weatherapp.repository

import android.content.Context
import android.util.Log
import com.shermanrex.weatherapp.jetpack.weatherapp.models.CurrentWeatherModel
import com.shermanrex.weatherapp.jetpack.weatherapp.models.ResponseResultModel
import com.shermanrex.weatherapp.jetpack.weatherapp.models.SevenDayForecastModel
import com.shermanrex.weatherapp.jetpack.weatherapp.models.ThreeHourWeatherModel
import com.shermanrex.weatherapp.jetpack.weatherapp.models.WeatherResponseMapKey
import com.shermanrex.weatherapp.jetpack.weatherapp.retrofit.RetrofitService
import com.shermanrex.weatherapp.jetpack.weatherapp.util.ConnectivityMonitor
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okio.IOException
import retrofit2.awaitResponse
import javax.inject.Inject
import javax.inject.Named

class WeatherRepository @Inject constructor(
    @Named("WeatherbitRetrofit") var weatherbitretrofit: RetrofitService,
    @Named("OpenWeatherRetrofit") var openweatherretrofit: RetrofitService,
    private var context: Context
) {

    var _WeatherReponse = MutableStateFlow<ResponseResultModel>(ResponseResultModel.Idle)
    var WeatherResponse: StateFlow<ResponseResultModel> = _WeatherReponse

    var _WeatherReponseError = MutableStateFlow<ResponseResultModel>(ResponseResultModel.Idle)
    var WeatherResponseError: StateFlow<ResponseResultModel> = _WeatherReponseError

    var WeatherResponseMap: MutableMap<String, Any> = mutableMapOf()

    private val networkConnectivity by lazy {
        ConnectivityMonitor(context)
    }

    suspend fun callWeatherApi(lat: Double, lon: Double, unit: String) {

        val coroutineExceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            _WeatherReponseError.value = ResponseResultModel.Error(throwable.message.toString())
        }


        CoroutineScope(Dispatchers.IO + coroutineExceptionHandler).launch {

            if (!networkConnectivity.checkNetworkConnection()) {
                _WeatherReponseError.value = ResponseResultModel.NetWork
            } else {

                _WeatherReponse.value = ResponseResultModel.Loading
                _WeatherReponseError.value = ResponseResultModel.Idle

                val currentResponse =
                    withContext(Dispatchers.Default) {
                        Log.d("TAG", "callWeatherApi: " + "getCurrentWeatherApi" )
                        openweatherretrofit.getCurrentWeatherApi(
                            lat,
                            lon,
                            unit
                        )
                    }
                val sevenDayResponse =
                    withContext(Dispatchers.Default) {
                        Log.d("TAG", "callWeatherApi: " + "getSevenDayWeatherApi" )
                        weatherbitretrofit.getSevenDayWeatherApi(
                            lat,
                            lon,
                            unit
                        )
                    }
                val threeHourResponse =
                    withContext(Dispatchers.Default) {
                        Log.d("TAG", "callWeatherApi: " + "getThreeHourWeatherApi" )
                        openweatherretrofit.getThreeHourWeatherApi(
                            lat,
                            lon,
                            unit
                        )
                    }


                if (currentResponse.isSuccessful && sevenDayResponse.isSuccessful && threeHourResponse.isSuccessful) {

                    WeatherResponseMap.clear()

                    WeatherResponseMap[WeatherResponseMapKey.CurrentForecast.toString()] =
                        currentResponse.body() as CurrentWeatherModel

                    WeatherResponseMap[WeatherResponseMapKey.Sevendayforecast.toString()] =
                        sevenDayResponse.body() as SevenDayForecastModel

                    WeatherResponseMap[WeatherResponseMapKey.ThreeHourforcast.toString()] =
                        threeHourResponse.body() as ThreeHourWeatherModel

                    _WeatherReponse.value = ResponseResultModel.Success(WeatherResponseMap)

                }
                else {
                    when {
                        !currentResponse.isSuccessful -> {
                            _WeatherReponseError.value =
                                ResponseResultModel.Error(currentResponse.message())
                            Log.d("TAG", "currentResponse: " + currentResponse.message())
                            return@launch
                        }

                        !sevenDayResponse.isSuccessful -> {
                            _WeatherReponseError.value =
                                ResponseResultModel.Error(sevenDayResponse.message())
                            Log.d("TAG", "sevenDayResponse: " + sevenDayResponse.message())
                            return@launch
                        }

                        !threeHourResponse.isSuccessful -> {
                            _WeatherReponseError.value =
                                ResponseResultModel.Error(threeHourResponse.message())
                            Log.d("TAG", "threeHourResponse: " + threeHourResponse.message())
                            return@launch
                        }
                    }
                }
            }
        }
    }
}