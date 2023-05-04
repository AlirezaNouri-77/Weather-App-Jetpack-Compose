package com.shermanrex.weatherapp.jetpack.weatherapp.repository

import android.util.Log
import com.shermanrex.weatherapp.jetpack.weatherapp.models.ResponseResult
import com.shermanrex.weatherapp.jetpack.weatherapp.models.SearchCityApiModel
import com.shermanrex.weatherapp.jetpack.weatherapp.retrofit.retrofitService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named

class SearchCityApiRepository @Inject constructor(@Named("SearchApiRetrofit") var retrofit: Retrofit) {

    var _resultStateFlow = MutableStateFlow<ResponseResult>(ResponseResult.Empty)
    var resultStateFlow:StateFlow<ResponseResult> = _resultStateFlow

    fun getSearchCityApiRepo(cityname:String){
        _resultStateFlow.value = ResponseResult.Loading
        retrofit.create(retrofitService::class.java).getSearchApi(
            cityName = cityname
        ).enqueue(object :Callback<SearchCityApiModel>{
            override fun onResponse(
                call: Call<SearchCityApiModel> ,
                response: Response<SearchCityApiModel>
            ) {
                if (response.body()!!.isEmpty()){
                    _resultStateFlow.value = ResponseResult.Error("Nothing Found! Please try again")

                }else if (response.isSuccessful){
                    _resultStateFlow.value = ResponseResult.SearchSuccess(response.body() as SearchCityApiModel)

                }

            }

            override fun onFailure(call: Call<SearchCityApiModel> , t: Throwable) {
                Log.d("TAG" , "onFailure: " + t.message)
            }

        })
    }
}