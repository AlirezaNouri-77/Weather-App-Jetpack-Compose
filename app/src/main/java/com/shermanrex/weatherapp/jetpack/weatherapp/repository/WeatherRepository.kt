package com.shermanrex.weatherapp.jetpack.weatherapp.repository

import android.content.Context
import android.util.Log
import com.shermanrex.weatherapp.jetpack.weatherapp.models.CurrentWeatherModel
import com.shermanrex.weatherapp.jetpack.weatherapp.models.SealedResponseResultModel
import com.shermanrex.weatherapp.jetpack.weatherapp.models.SevenDayForecastModel
import com.shermanrex.weatherapp.jetpack.weatherapp.models.ThreeHourWeatherModel
import com.shermanrex.weatherapp.jetpack.weatherapp.models.WeatherResponseData
import com.shermanrex.weatherapp.jetpack.weatherapp.retrofit.RetrofitService
import com.shermanrex.weatherapp.jetpack.weatherapp.util.ConnectivityMonitor
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class WeatherRepository @Inject constructor(
    @Named("WeatherbitRetrofit") var weatherbitretrofit: RetrofitService,
    @Named("OpenWeatherRetrofit") var openweatherretrofit: RetrofitService,
    private var context: Context
) {

    var _WeatherReponse = MutableStateFlow<SealedResponseResultModel>(SealedResponseResultModel.Idle)
    var WeatherResponse: StateFlow<SealedResponseResultModel> = _WeatherReponse

    var _WeatherReponseError = MutableStateFlow<SealedResponseResultModel>(SealedResponseResultModel.Idle)
    var WeatherResponseError: StateFlow<SealedResponseResultModel> = _WeatherReponseError

    private val networkConnectivity by lazy {
        ConnectivityMonitor(context)
    }

    suspend fun callWeatherApi(lat: Double, lon: Double, unit: String) {

        val coroutineExceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            _WeatherReponseError.value = SealedResponseResultModel.Error(throwable.message.toString())
            Log.d("TAG", "callWeatherApi: " + throwable.message.toString())
        }

        CoroutineScope(Dispatchers.IO + coroutineExceptionHandler).launch {

            if (!networkConnectivity.checkNetworkConnection()) {
                _WeatherReponseError.value = SealedResponseResultModel.NetWork
            } else {

                _WeatherReponse.value = SealedResponseResultModel.Loading
                _WeatherReponseError.value = SealedResponseResultModel.Idle

                val currentResponse =
                    withContext(Dispatchers.IO) {
                        Log.d("TAG", "callWeatherApi: " + "getCurrentWeatherApi" )
                        openweatherretrofit.getCurrentWeatherApi(
                            lat,
                            lon,
                            unit
                        )
                    }
                val sevenDayResponse =
                    withContext(Dispatchers.IO) {
                        Log.d("TAG", "callWeatherApi: " + "getSevenDayWeatherApi" )
                        weatherbitretrofit.getSevenDayWeatherApi(
                            lat,
                            lon,
                            unit
                        )
                    }
                val threeHourResponse =
                    withContext(Dispatchers.IO) {
                        Log.d("TAG", "callWeatherApi: " + "getThreeHourWeatherApi" )
                        openweatherretrofit.getThreeHourWeatherApi(
                            lat,
                            lon,
                            unit
                        )
                    }


                if (currentResponse.isSuccessful && sevenDayResponse.isSuccessful && threeHourResponse.isSuccessful) {

                    _WeatherReponse.value = SealedResponseResultModel.Success(
                        data = WeatherResponseData(
                            currentWeatherData =  currentResponse.body() as CurrentWeatherModel,
                            sevenDayWeatherData = sevenDayResponse.body() as SevenDayForecastModel,
                            threeHourWeatherData = threeHourResponse.body() as ThreeHourWeatherModel
                        )
                    )

                }
                else {
                    when {
                        !currentResponse.isSuccessful -> {
                            _WeatherReponseError.value =
                                SealedResponseResultModel.Error(currentResponse.message())
                            Log.d("TAG", "currentResponse: " + currentResponse.message())
                            return@launch
                        }

                        !sevenDayResponse.isSuccessful -> {
                            _WeatherReponseError.value =
                                SealedResponseResultModel.Error(sevenDayResponse.message())
                            Log.d("TAG", "sevenDayResponse: " + sevenDayResponse.message())
                            return@launch
                        }

                        !threeHourResponse.isSuccessful -> {
                            _WeatherReponseError.value =
                                SealedResponseResultModel.Error(threeHourResponse.message())
                            Log.d("TAG", "threeHourResponse: " + threeHourResponse.message())
                            return@launch
                        }
                    }
                }
            }
        }
    }
}