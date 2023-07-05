package com.shermanrex.weatherapp.jetpack.weatherapp.repository

import android.content.Context
import com.shermanrex.weatherapp.jetpack.weatherapp.models.SearchCityApiModel
import com.shermanrex.weatherapp.jetpack.weatherapp.models.ResponseResultModel
import com.shermanrex.weatherapp.jetpack.weatherapp.retrofit.RetrofitService
import com.shermanrex.weatherapp.jetpack.weatherapp.util.ConnectivityMonitor
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class SearchCityRepository @Inject constructor(
    @Named("SearchApiRetrofit") var retrofit: RetrofitService,
    private var context: Context
) {

    val networkConnection by lazy {
        ConnectivityMonitor(context = context)
    }


    private var _SearchApiResultStateflow =
        MutableStateFlow<ResponseResultModel>(ResponseResultModel.Idle)
    var searchApiResultStateflow: StateFlow<ResponseResultModel> = _SearchApiResultStateflow

    var coroutineExceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        _SearchApiResultStateflow.value = ResponseResultModel.Error(throwable.message.toString())
    }

    fun getSearchCityApiRepo(cityname: String) = CoroutineScope(Dispatchers.IO + coroutineExceptionHandler).launch {

        if (!networkConnection.checkNetworkConnection()) {
            _SearchApiResultStateflow.value =
                ResponseResultModel.Error("Please check your internet connection")
            return@launch
        }

        _SearchApiResultStateflow.value = ResponseResultModel.Loading

        val response = retrofit.getSearchApi(
            cityName = cityname.lowercase()
        )
        withContext(Dispatchers.IO) {
            when {
                response.body().toString().isEmpty() -> {
                    _SearchApiResultStateflow.value =
                        ResponseResultModel.Error("Nothing Found! Please try again")
                }

                response.isSuccessful -> {
                    _SearchApiResultStateflow.value =
                        ResponseResultModel.SearchSuccess(response.body() as SearchCityApiModel)
                }

                !response.isSuccessful -> {
                    _SearchApiResultStateflow.value =
                        ResponseResultModel.Error(response.message())
                }
            }

        }
    }
}

