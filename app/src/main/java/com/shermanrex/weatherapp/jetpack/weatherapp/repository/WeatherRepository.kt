package com.shermanrex.weatherapp.jetpack.weatherapp.repository

import android.util.Log
import com.shermanrex.weatherapp.jetpack.weatherapp.datastore.DataStoreManager
import com.shermanrex.weatherapp.jetpack.weatherapp.models.remote.weatherModels.CurrentWeatherModel
import com.shermanrex.weatherapp.jetpack.weatherapp.models.remote.ResponseModel
import com.shermanrex.weatherapp.jetpack.weatherapp.models.remote.weatherModels.SevenDayForecastModel
import com.shermanrex.weatherapp.jetpack.weatherapp.models.remote.weatherModels.ThreeHourWeatherModel
import com.shermanrex.weatherapp.jetpack.weatherapp.models.remote.WeatherResponseData
import com.shermanrex.weatherapp.jetpack.weatherapp.retrofit.RetrofitService
import com.shermanrex.weatherapp.jetpack.weatherapp.util.NetworkConnectivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.internal.ChannelFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.SocketException
import javax.inject.Inject
import javax.inject.Named

class WeatherRepository @Inject constructor(
  @Named("WeatherbitRetrofit") private var weatherbitretrofit: RetrofitService,
  @Named("OpenWeatherRetrofit") private var openweatherretrofit: RetrofitService,
  private var dataStoreManager: DataStoreManager,
) {
  suspend fun callWeatherRepository(
	lat: Double,
	lon: Double,
	unit: String,
  ): Flow<ResponseModel> {
	return channelFlow {
	  send(ResponseModel.Loading)
	  try {
		val currentResponse = async {
		  openweatherretrofit.getCurrentWeatherApi(
			lat,
			lon,
			unit
		  )
		}.await()
		
		val sevenDayResponse = async {
		  weatherbitretrofit.getSevenDayWeatherApi(
			lat,
			lon,
			unit
		  )
		}.await()
		
		val threeHourResponse = async {
		  openweatherretrofit.getThreeHourWeatherApi(
			lat,
			lon,
			unit
		  )
		}.await()
		
		Log.d("URL", "currentResponse: " + currentResponse.raw().request.url)
		Log.d("URL", "sevenDayResponse: " + sevenDayResponse.raw().request.url)
		Log.d("URL", "threeHourResponse: " + threeHourResponse.raw().request.url)
		
		if (currentResponse.isSuccessful && sevenDayResponse.isSuccessful && threeHourResponse.isSuccessful) {
		  dataStoreManager.writeCoordinatorDataStore(lat = lat, lon = lon)
		  send(
			ResponseModel.Success(
			  data = WeatherResponseData(
				currentWeatherData = currentResponse.body() as CurrentWeatherModel,
				sevenDayWeatherData = sevenDayResponse.body() as SevenDayForecastModel,
				threeHourWeatherData = threeHourResponse.body() as ThreeHourWeatherModel
			  )
			)
		  )
		} else if (!currentResponse.isSuccessful || !sevenDayResponse.isSuccessful || !threeHourResponse.isSuccessful) {
		  send(ResponseModel.Error("Fail to get data from server"))
		}
		
	  } catch (e: SocketException) {
		send(ResponseModel.Error("Connection Timeout"))
	  } catch (e: IOException) {
		send(ResponseModel.Error("Connection Timeout"))
	  }
	}
  }
  
}
