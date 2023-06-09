package com.shermanrex.weatherapp.jetpack.weatherapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shermanrex.weatherapp.jetpack.weatherapp.models.ResponseResultModel
import com.shermanrex.weatherapp.jetpack.weatherapp.repository.WeatherRepository
import com.shermanrex.weatherapp.jetpack.weatherapp.util.locationPermission
import com.shermanrex.weatherapp.jetpack.weatherapp.util.myDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(

    private var WeatherRepository: WeatherRepository,
    private var myDataStore: myDataStore,
    private var locationPermission: locationPermission,

    ) : ViewModel() {

    fun callWeatherRepository(lat: Double = 0.0, lon: Double = 0.0, unit: String = "") =
        viewModelScope.launch {

            val dataStore = myDataStore.dataStoreFlow().stateIn(viewModelScope).value

            // Check if location permission is granted use gps for get coordinator
            if (lat != 0.0) {
                WeatherRepository.getCurrent(
                    lat,
                    lon,
                    myDataStore.unitFlow
                )
                // if permission is not granted and search in search page for coordinator
            } else if (locationPermission.isPermissionGranted()) {
                withContext(Dispatchers.Default) {
                    WeatherRepository.getCurrent(
                        locationPermission.getLocationCoordinator()[0].lat!!,
                        locationPermission.getLocationCoordinator()[0].lon!!,
                        myDataStore.unitFlow
                    )
                }
                // if above two statements is not ture which mean user before call and coordinator save in datastore
            } else if (dataStore.lat != 0.0) {
                WeatherRepository.getCurrent(dataStore.lat, dataStore.lon, myDataStore.unitFlow)
                // When app launch First Time
            } else if (!locationPermission.isPermissionGranted()) {
                WeatherRepository.setWeatherResposneResult()
            }
        }

    fun weatherApiResponse(): StateFlow<ResponseResultModel> {
        return WeatherRepository.WeatherResponse
    }

    fun updateCoordinatorDataStore(lat: Double = 0.0, lon: Double = 0.0) =
        viewModelScope.launch {
            myDataStore.writeCoordinatorDataStore(lat, lon)
        }

    fun updateUnitDataStore(unit: String) = viewModelScope.launch {
        myDataStore.writeUnitDataStore(unit)
    }

    fun getWeatherUnitDataStore(): String  {
        return myDataStore.unitFlow
    }

}




