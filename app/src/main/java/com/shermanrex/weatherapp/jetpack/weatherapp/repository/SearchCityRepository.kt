package com.shermanrex.weatherapp.jetpack.weatherapp.repository

import android.content.Context
import com.shermanrex.weatherapp.jetpack.weatherapp.models.SearchCityApiModel
import com.shermanrex.weatherapp.jetpack.weatherapp.models.SealedResponseResultModel
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

    private val networkConnection by lazy {
        ConnectivityMonitor(context = context)
    }

    private var _SearchApiResultStateflow =
        MutableStateFlow<SealedResponseResultModel>(SealedResponseResultModel.Idle)
    var searchApiResultStateflow: StateFlow<SealedResponseResultModel> = _SearchApiResultStateflow

    var coroutineExceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        _SearchApiResultStateflow.value = SealedResponseResultModel.Error(throwable.message.toString())
    }

    fun getSearchCityApiRepo(cityname: String) = CoroutineScope(Dispatchers.IO + coroutineExceptionHandler).launch {

        if (!networkConnection.checkNetworkConnection()) {
            _SearchApiResultStateflow.value =
                SealedResponseResultModel.Error("Please check your internet connection")
            return@launch
        }

        _SearchApiResultStateflow.value = SealedResponseResultModel.Loading

        val response = retrofit.getSearchApi(
            cityName = cityname
        )

        withContext(Dispatchers.IO) {
            when {
                response.isSuccessful -> {
                    if (response.body()!!.isNotEmpty()){
                        _SearchApiResultStateflow.value =
                            SealedResponseResultModel.SearchSuccess(response.body() as SearchCityApiModel)
                    } else {
                        _SearchApiResultStateflow.value =
                            SealedResponseResultModel.Error("Nothing Found! Please try again")
                    }
                }

                !response.isSuccessful -> {
                    _SearchApiResultStateflow.value =
                        SealedResponseResultModel.Error(response.message())
                }
            }
        }
    }
}

