package com.shermanrex.weatherapp.jetpack.weatherapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shermanrex.weatherapp.jetpack.weatherapp.models.remote.ResponseModel
import com.shermanrex.weatherapp.jetpack.weatherapp.repository.SearchCityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
  private var searchCityRepository: SearchCityRepository,
) : ViewModel() {
  
  private var _searchApiResultStateflow =
	MutableStateFlow<ResponseModel>(ResponseModel.Idle)
  var searchApiResultStateflow: StateFlow<ResponseModel> = _searchApiResultStateflow
  
  fun callSearchCityApi(cityname: String) = viewModelScope.launch {
	searchCityRepository.getSearchApi(cityname).collectLatest { state ->
	  _searchApiResultStateflow.update { state }
	}
  }
  
}