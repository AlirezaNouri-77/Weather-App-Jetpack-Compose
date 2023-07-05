package com.shermanrex.weatherapp.jetpack.weatherapp.util

import android.content.Context
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.TRANSPORT_CELLULAR
import android.net.NetworkCapabilities.TRANSPORT_ETHERNET
import android.net.NetworkCapabilities.TRANSPORT_WIFI
import android.os.Build

class ConnectivityMonitor(
    var context: Context
) {

    private var connectivtymanager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    fun checkNetworkConnection(): Boolean {
        var resultNetwork: Boolean
        val networkCapab = connectivtymanager.activeNetwork ?: return false
        val activeNetwork = connectivtymanager.getNetworkCapabilities(networkCapab) ?: return false

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