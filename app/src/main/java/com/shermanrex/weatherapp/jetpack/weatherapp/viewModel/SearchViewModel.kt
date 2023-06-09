package com.shermanrex.weatherapp.jetpack.weatherapp.viewModel

import androidx.lifecycle.ViewModel
import com.shermanrex.weatherapp.jetpack.weatherapp.models.ResponseResultModel
import com.shermanrex.weatherapp.jetpack.weatherapp.repository.SearchCityApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private var searchCityApiRepository: SearchCityApiRepository,
) : ViewModel() {

    fun callSearchCityApi(cityname: String) {
        searchCityApiRepository.getSearchCityApiRepo(cityname)
    }

    fun searchCityApiResponse(): StateFlow<ResponseResultModel> {
        return searchCityApiRepository.searchApiResultStateflow
    }

}