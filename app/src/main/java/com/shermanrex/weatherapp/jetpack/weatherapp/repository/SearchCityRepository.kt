package com.shermanrex.weatherapp.jetpack.weatherapp.repository

import android.content.Context
import android.util.Log
import com.shermanrex.weatherapp.jetpack.weatherapp.models.remote.ResponseModel
import com.shermanrex.weatherapp.jetpack.weatherapp.retrofit.RetrofitService
import com.shermanrex.weatherapp.jetpack.weatherapp.util.NetworkConnectivity
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.update
import java.io.IOException
import java.net.SocketException
import javax.inject.Inject
import javax.inject.Named

class SearchCityRepository @Inject constructor(
  @Named("SearchApiRetrofit") var retrofit: RetrofitService,
  private var networkConnectivity: NetworkConnectivity,
) {
  
  fun getSearchApi(cityName: String): Flow<ResponseModel> {
	return channelFlow {
	  
	  if (!networkConnectivity.checkNetworkConnection()) {
		send(ResponseModel.Error("Please check your internet connection"))
	  } else {
		send(ResponseModel.Loading)
		try {
		  
		  val response = async {
			retrofit.getSearchApi(
			  cityName = cityName
			)
		  }.await()
		  
		  when {
			
			response.isSuccessful -> {
			  if (response.body()!!.isNotEmpty()) {
				send(ResponseModel.Success(response.body()))
			  } else {
				send(ResponseModel.Error("Nothing Found! Please try again"))
			  }
			}
			
			!response.isSuccessful -> {
			  send(ResponseModel.Error(response.message()))
			}
			
		  }
		} catch (e: Exception) {
		  send(ResponseModel.Error(e.message.toString()))
		} catch (e: SocketException) {
		  send(ResponseModel.Error("Connection Timeout"))
		} catch (e: IOException) {
		  send(ResponseModel.Error("Connection Timeout"))
		}
		
	  }
	}
  }
}

