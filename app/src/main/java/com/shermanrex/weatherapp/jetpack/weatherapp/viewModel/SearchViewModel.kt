package com.shermanrex.weatherapp.jetpack.weatherapp.viewModel

import androidx.lifecycle.ViewModel
import com.shermanrex.weatherapp.jetpack.weatherapp.models.SealedResponseResultModel
import com.shermanrex.weatherapp.jetpack.weatherapp.repository.SearchCityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private var searchCityRepository: SearchCityRepository,
) : ViewModel() {

    fun callSearchCityApi(cityname: String) {
        searchCityRepository.getSearchCityApiRepo(cityname)
    }

    fun searchCityApiResponse(): StateFlow<SealedResponseResultModel> {
        return searchCityRepository.searchApiResultStateflow
    }

}