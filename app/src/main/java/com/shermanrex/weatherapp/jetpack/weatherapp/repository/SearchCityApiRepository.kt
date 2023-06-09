package com.shermanrex.weatherapp.jetpack.weatherapp.repository

import com.shermanrex.weatherapp.jetpack.weatherapp.models.SearchCityApiModel
import com.shermanrex.weatherapp.jetpack.weatherapp.models.ResponseResultModel
import com.shermanrex.weatherapp.jetpack.weatherapp.retrofit.RetrofitService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class SearchCityApiRepository @Inject constructor(@Named("SearchApiRetrofit") var retrofit: RetrofitService) {

    var _SearchApiResultStateflow = MutableStateFlow<ResponseResultModel>(ResponseResultModel.Idle)
    var searchApiResultStateflow: StateFlow<ResponseResultModel> = _SearchApiResultStateflow

    fun getSearchCityApiRepo(cityname: String) {
        _SearchApiResultStateflow.value = ResponseResultModel.Loading
        CoroutineScope(Dispatchers.IO).launch {
            val response = retrofit.getSearchApi(
                cityName = cityname
            )
            withContext(Dispatchers.IO){
                if (response.body().toString().isEmpty()) {
                    _SearchApiResultStateflow.value =
                        ResponseResultModel.Error("Nothing Found! Please try again")

                } else if (response.isSuccessful) {
                    _SearchApiResultStateflow.value =
                        ResponseResultModel.SearchSuccess(response.body() as SearchCityApiModel)
                }
            }
        }

    }
}