package com.shermanrex.weatherapp.jetpack.weatherapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shermanrex.weatherapp.jetpack.weatherapp.models.ResponseResultModel
import com.shermanrex.weatherapp.jetpack.weatherapp.repository.SearchCityApiRepository
import com.shermanrex.weatherapp.jetpack.weatherapp.repository.WeatherRepository
import com.shermanrex.weatherapp.jetpack.weatherapp.util.WeatherDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class MyviewModel @Inject constructor(
    private var searchCityApiRepository: SearchCityApiRepository ,
    private var WeatherRepository: WeatherRepository ,
    private var WeatherDataStore: WeatherDataStore ,
) : ViewModel() {

    fun callWeatherRepository(lat: Double , lon: Double , unit:String) = viewModelScope.launch {
        WeatherDataStore.flow.collectLatest {
            if (lat != 0.0) {
                withContext(Dispatchers.Default) { WeatherRepository.getCurrent(lat , lon ,unit ) }
            } else if (it.lat != 0.0) {
                withContext(Dispatchers.Default) {
                    WeatherRepository.getCurrent(it.lat , it.lon , it.unit)
                }
            } else {
                WeatherRepository._WeatherReponse.value = ResponseResultModel.Empty
            }
        }
    }

    fun weatherApiResponse(): StateFlow<ResponseResultModel> {
        return WeatherRepository.WeatherResponse
    }

    fun callSearchCityApi(cityname: String) {
        searchCityApiRepository.getSearchCityApiRepo(cityname)
    }

    fun searchCityApiResponse(): StateFlow<ResponseResultModel> {
        return searchCityApiRepository.resultStateFlow
    }

    fun updateDataStore(lat: Double , lon: Double , unit: String) = viewModelScope.launch {
        WeatherDataStore.writeDataStore(lat , lon , unit)
    }

    fun getunitdataStore(): String {
        var unit = ""
        viewModelScope.launch {
            WeatherDataStore.unitFlow.collectLatest {
                unit = it
            }
        }
        return unit
    }
}




