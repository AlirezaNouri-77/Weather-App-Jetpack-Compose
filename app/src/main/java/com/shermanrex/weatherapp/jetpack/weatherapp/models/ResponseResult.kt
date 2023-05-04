package com.shermanrex.weatherapp.jetpack.weatherapp.models

sealed class ResponseResult {

    data class Success(val data: MutableMap<String,Any>) : ResponseResult()

    data class SearchSuccess(val data: SearchCityApiModel) : ResponseResult()

    data class Error(val error: String) : ResponseResult()

    object Empty : ResponseResult()

    object Loading : ResponseResult()

}
