package com.shermanrex.weatherapp.jetpack.weatherapp.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shermanrex.weatherapp.jetpack.weatherapp.datastore.DataStoreManager
import com.shermanrex.weatherapp.jetpack.weatherapp.models.remote.ResponseModel
import com.shermanrex.weatherapp.jetpack.weatherapp.models.remote.WeatherResponseData
import com.shermanrex.weatherapp.jetpack.weatherapp.repository.WeatherRepository
import com.shermanrex.weatherapp.jetpack.weatherapp.util.LocationManager
import com.shermanrex.weatherapp.jetpack.weatherapp.util.NetworkConnectivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
  private var weatherRepository: WeatherRepository,
  private var dataStoreManager: DataStoreManager,
  private var locationManager: LocationManager,
  private var networkConnectivity: NetworkConnectivity,
) : ViewModel() {
  
  var weatherData = mutableStateOf(WeatherResponseData())
  
  private var _weatherResponseState = MutableStateFlow<ResponseModel>(ResponseModel.Idle)
  val weatherResponse: StateFlow<ResponseModel> = _weatherResponseState
  
  init {
	getWeather()
  }

  fun getWeatherByCoordinator() =
	viewModelScope.launch {
	  if (!networkConnectivity.checkNetworkConnection()) {
		_weatherResponseState.value = ResponseModel.UnAvailableNetwork
	  }
	  val coordinator = async { dataStoreManager.getCoordinator() }.await()
	  weatherRepository.callWeatherRepository(
		lat = coordinator.lat,
		lon = coordinator.lon,
		unit = coordinator.unit,
	  ).collect {
		_weatherResponseState.value = it
	  }
	}
  
  fun getWeather() = viewModelScope.launch {
	if (!networkConnectivity.checkNetworkConnection()) {
	  _weatherResponseState.value = ResponseModel.UnAvailableNetwork
	  return@launch
	} else {
	  if (locationManager.isLocationPermissionGranted()) {
		if (locationManager.checkGpsOn()) {
		  weatherRepository.callWeatherRepository(
			lat = locationManager.getLocationCoordinator().lat.toDouble(),
			lon = locationManager.getLocationCoordinator().lon.toDouble(),
			unit = getWeatherUnitDataStore(),
		  ).collect {
			_weatherResponseState.value =  it
		  }
		} else {
		  _weatherResponseState.value = ResponseModel.UnAvailableGpsService
		}
	  } else {
		if (dataStoreManager.checkDataStoreIsEmpty) {
		  getWeatherByCoordinator()
		}
	  }
	}
  }
  
  fun messageShowed() {
	_weatherResponseState.update { ResponseModel.Idle }
  }
  
  fun updateCoordinatorDataStore(latitude: Double, longitude: Double) = viewModelScope.launch {
	dataStoreManager.writeCoordinatorDataStore(lat = latitude, lon = longitude)
  }
  
  fun updateUnitDataStore(unit: String) = viewModelScope.launch {
	dataStoreManager.writeUnitDataStore(unit)
  }
  
  fun getWeatherUnitDataStore(): String {
	return dataStoreManager.getUnitDataStore
  }
  
  fun checkDataStoreIsEmpty(): Boolean {
	return dataStoreManager.checkDataStoreIsEmpty
  }
  
}
