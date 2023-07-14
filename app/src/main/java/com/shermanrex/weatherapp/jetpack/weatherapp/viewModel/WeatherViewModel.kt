package com.shermanrex.weatherapp.jetpack.weatherapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shermanrex.weatherapp.jetpack.weatherapp.models.ResponseResultModel
import com.shermanrex.weatherapp.jetpack.weatherapp.repository.WeatherRepository
import com.shermanrex.weatherapp.jetpack.weatherapp.util.LocationUtil
import com.shermanrex.weatherapp.jetpack.weatherapp.datastore.MyDataStore
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
    private var myDataStore: MyDataStore,
    private var LocationUtil: LocationUtil

    ) : ViewModel() {


    fun callWeatherRepositorySearchScreen(lat: Double = 0.0, lon: Double = 0.0) {
        viewModelScope.launch {
            WeatherRepository.callWeatherApi(
                lat,
                lon,
                myDataStore.getUnitDataStore
            )
        }
    }

    fun callWeatherRepository() = viewModelScope.launch {

           val dataStore = myDataStore.dataStoreFlow().stateIn(viewModelScope).value

           if (LocationUtil.isPermissionGranted()) {
                if (LocationUtil.checkLocation()) {
                    withContext(Dispatchers.Default) {
                        WeatherRepository.callWeatherApi(
                            LocationUtil.getLocationCoordinator().first().lat!!,
                            LocationUtil.getLocationCoordinator().first().lon!!,
                            myDataStore.getUnitDataStore
                        )
                    }
                } else {
                    WeatherRepository._WeatherReponseError.value = ResponseResultModel.LocationNotOn
                }
                // if above two statements is not ture which mean user before call and coordinator save in datastore
            } else if (dataStore.lat != 0.0) {
                WeatherRepository.callWeatherApi(dataStore.lat, dataStore.lon, myDataStore.getUnitDataStore)
                // When app launch First Time
            } else if (!LocationUtil.isPermissionGranted()) {
               WeatherRepository._WeatherReponse.value = ResponseResultModel.Empty
            }
        }

    fun weatherApiResponse(): StateFlow<ResponseResultModel> {
        return WeatherRepository.WeatherResponse
    }

    fun updateWeatherResponseError(input:ResponseResultModel){
        WeatherRepository._WeatherReponseError.value = input
    }

    fun weatherApiResponseError(): StateFlow<ResponseResultModel> {
        return WeatherRepository.WeatherResponseError
    }

    fun updateCoordinatorDataStore(lat: Double = 0.0, lon: Double = 0.0) =
        viewModelScope.launch {
            myDataStore.writeCoordinatorDataStore(lat, lon)
        }

    fun updateUnitDataStore(unit: String) = viewModelScope.launch {
        myDataStore.writeUnitDataStore(unit)
    }

    fun getWeatherUnitDataStore(): String {
        return myDataStore.getUnitDataStore
    }

    fun checkLocationGranted():Boolean{
        return LocationUtil.isPermissionGranted()
    }

}




