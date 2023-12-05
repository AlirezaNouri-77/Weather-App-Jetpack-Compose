package com.shermanrex.weatherapp.jetpack.weatherapp.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import androidx.core.content.ContextCompat
import com.shermanrex.weatherapp.jetpack.weatherapp.models.LocationCoordinatorModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class LocationManager @Inject constructor(
  @ApplicationContext var context: Context
) {
  
  private val locationManager by lazy {
	context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
  }
  
  fun checkGpsOn(): Boolean {
	val resultLocation: Boolean = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
	  when (locationManager.isLocationEnabled) {
		true -> true
		else -> false
	  }
	  
	} else {
	  when (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
		true -> true
		false -> false
	  }
	}
	return resultLocation
  }
  
  fun getLocationCoordinator(): LocationCoordinatorModel {
	var result: LocationCoordinatorModel = LocationCoordinatorModel().copy(lat = 0.0f, lon = 0.0f)
	val check = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
	  ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
	} else {
	  ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
	}
	if (check == PackageManager.PERMISSION_GRANTED) {
	  val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
	  result = LocationCoordinatorModel(
		lon = location!!.longitude.toFloat(),
		lat = location.latitude.toFloat()
	  )
	}
	return result
  }
  
  fun isLocationPermissionGranted(): Boolean {
	return ContextCompat.checkSelfPermission(
	  context,
	  Manifest.permission.ACCESS_COARSE_LOCATION
	) == PackageManager.PERMISSION_GRANTED
  }
  
  
}

