package com.shermanrex.weatherapp.jetpack.weatherapp.models

sealed class SealedResponseResultModel {
    data class Success(val data: WeatherResponseData) : SealedResponseResultModel()
    data class SearchSuccess(val data: SearchCityApiModel) : SealedResponseResultModel()
    data class Error(val error: String) : SealedResponseResultModel()
    object Idle : SealedResponseResultModel()
    object Loading : SealedResponseResultModel()
    object Empty : SealedResponseResultModel()
    object NetWork : SealedResponseResultModel()
    object LocationNotOn : SealedResponseResultModel()
    object Location : SealedResponseResultModel()

}

