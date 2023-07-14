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
    object Location:ResponseResultModel()

}

//sealed class ResponseResultModel<out T> {
//    data class Success<T>(val data: T?) : ResponseResultModel<T>()
//    data class SearchSuccess<T>(val data: T?) : ResponseResultModel<T>()
//    data class Error<T>(val error: T?) : ResponseResultModel<T>()
//    object Idle : ResponseResultModel<Nothing>()
//    object Loading : ResponseResultModel<Nothing>()
//    object Empty : ResponseResultModel<Nothing>()
//    object NetWork : ResponseResultModel<Nothing>()
//    object LocationNotOn : ResponseResultModel<Nothing>()
//    object Location : ResponseResultModel<Nothing>()
//
//}
