package com.shermanrex.weatherapp.jetpack.weatherapp.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.TRANSPORT_CELLULAR
import android.net.NetworkCapabilities.TRANSPORT_ETHERNET
import android.net.NetworkCapabilities.TRANSPORT_WIFI

class NetworkConnectivity(
  var context: Context
) {
  
  private val connectivityManager by lazy {
	context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
  }
  fun checkNetworkConnection(): Boolean {
	val resultNetwork: Boolean
	val networkCapable = connectivityManager.activeNetwork ?: return false
	val activeNetwork = connectivityManager.getNetworkCapabilities(networkCapable) ?: return false
	resultNetwork = when {
	  activeNetwork.hasTransport(TRANSPORT_WIFI) -> true
	  activeNetwork.hasTransport(TRANSPORT_CELLULAR) -> true
	  activeNetwork.hasTransport(TRANSPORT_ETHERNET) -> true
	  else -> {
		false
	  }
	}
	return resultNetwork
  }
  
}