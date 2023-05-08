package com.shermanrex.weatherapp.jetpack.weatherapp.models

sealed class ResponseResultModel {

    data class Success(val data: Any) : ResponseResultModel()
    data class SearchSuccess(val data: Any) : ResponseResultModel()
    data class Error(val error: String) : ResponseResultModel()
    object Idle : ResponseResultModel()
    object Loading : ResponseResultModel()

}
