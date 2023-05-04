package com.shermanrex.weatherapp.jetpack.weatherapp.retrofit

import com.shermanrex.weatherapp.jetpack.weatherapp.models.ResponseResult
import com.shermanrex.weatherapp.jetpack.weatherapp.models.SearchCityApiModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface retrofitService {

    @GET("direct?")
    fun getSearchApi(
        @Query("limit") limit:Int = 10 ,
        @Query("appid") Apikey:String = "0368bc9fc5528ea37021049634b33bbf",
        @Query("q") cityName:String
    ): Call<SearchCityApiModel>

}