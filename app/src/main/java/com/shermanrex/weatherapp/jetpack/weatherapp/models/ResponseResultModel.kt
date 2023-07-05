package com.shermanrex.weatherapp.jetpack.weatherapp.models

sealed class ResponseResultModel {
    data class Success(val data: MutableMap<String, Any>) : ResponseResultModel()
    data class SearchSuccess(val data: SearchCityApiModel) : ResponseResultModel()
    data class Error(val error: String) : ResponseResultModel()
    object Idle : ResponseResultModel()
    object Loading : ResponseResultModel()
    object Empty:ResponseResultModel()
    object NetWork:ResponseResultModel()
    object LocationNotOn:ResponseResultModel()

}

sealed class ResponseResultModel1<out T> {
    data class Success<T>(val data: MutableMap<String, T>) : ResponseResultModel()
    data class SearchSuccess<T>(val data: T) : ResponseResultModel()
    data class Error<T>(val error: String) : ResponseResultModel()
    object Idle1 : ResponseResultModel()
    object Loading : ResponseResultModel()
    object Empty:ResponseResultModel()
    object NetWork:ResponseResultModel()

}
