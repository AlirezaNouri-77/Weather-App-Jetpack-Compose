package com.shermanrex.weatherapp.jetpack.weatherapp.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import androidx.core.content.ContextCompat
import com.shermanrex.weatherapp.jetpack.weatherapp.models.LocationCoordinator
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class LocationUtil @Inject constructor(
    @ApplicationContext var context: Context
) {

    private val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    fun checkLocation():Boolean {
        val resultLocation : Boolean = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            when (locationManager.isLocationEnabled) {
                true -> true
                else -> false
            }

        } else {
            when (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                true -> true
                false -> false
            }
        }
        return resultLocation
    }

    fun getLocationCoordinator(): MutableList<LocationCoordinator> {
        val list:MutableList<LocationCoordinator> = mutableListOf()
        list.clear()
        val check = ContextCompat.checkSelfPermission(context,Manifest.permission.ACCESS_COARSE_LOCATION)
        if (check==PackageManager.PERMISSION_GRANTED){
            val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            list.add(
                LocationCoordinator(
                    lon = location?.longitude,
                    lat = location?.latitude
                )
            )
        }
        return list
    }

    fun isPermissionGranted(): Boolean {
        val checkPermission = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        return checkPermission == PackageManager.PERMISSION_GRANTED
    }


}

