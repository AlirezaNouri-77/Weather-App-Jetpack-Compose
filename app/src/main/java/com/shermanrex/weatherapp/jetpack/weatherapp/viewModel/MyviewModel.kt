package com.shermanrex.weatherapp.jetpack.weatherapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shermanrex.weatherapp.jetpack.weatherapp.repository.SearchCityApiRepository
import com.shermanrex.weatherapp.jetpack.weatherapp.repository.WeatherRepository
import com.shermanrex.weatherapp.jetpack.weatherapp.models.ResponseResultModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MyviewModel @Inject constructor(
    private var searchCityApiRepository: SearchCityApiRepository ,
    private var WeatherRepository: WeatherRepository
) : ViewModel() {

    fun callWeatherRepository() = viewModelScope.launch {
        withContext(Dispatchers.Default) { WeatherRepository.getCurrent() }
        withContext(Dispatchers.Default) { WeatherRepository.getSevenDayForecast() }
        withContext(Dispatchers.Default) { WeatherRepository.getThreeHourForecast() }
    }

    fun weatherApiResponse():StateFlow<ResponseResultModel>{
        return WeatherRepository.WeatherResponse
    }

    fun callSearchCityApi(cityname: String) {
        searchCityApiRepository.getSearchCityApiRepo(cityname)
    }

    fun searchCityApiResponse(): StateFlow<ResponseResultModel> {
        return searchCityApiRepository.resultStateFlow
    }



}