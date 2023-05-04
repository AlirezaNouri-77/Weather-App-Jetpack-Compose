package com.shermanrex.weatherapp.jetpack.weatherapp.viewModel

import androidx.lifecycle.ViewModel
import com.shermanrex.weatherapp.jetpack.weatherapp.repository.SearchCityApiRepository
import com.shermanrex.weatherapp.jetpack.weatherapp.models.ResponseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SearchCityApiViewModel @Inject constructor(private var searchCityApiRepository: SearchCityApiRepository) :
    ViewModel() {

    fun callSearchCityApi(cityname: String) {
        searchCityApiRepository.getSearchCityApiRepo(cityname)
    }

    fun searchCityApiResponse(): StateFlow<ResponseResult> {
        return searchCityApiRepository.resultStateFlow
    }

}