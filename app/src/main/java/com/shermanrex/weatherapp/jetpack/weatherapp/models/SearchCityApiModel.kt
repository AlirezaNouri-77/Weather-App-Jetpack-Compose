package com.shermanrex.weatherapp.jetpack.weatherapp.models

class SearchCityApiModel : ArrayList<SearchModelItem>()

data class SearchModelItem(
    var name: String ,
    var lat: String ,
    var lon: String ,
    var country: String ,
    var state: String? ,
    var local_names: local_names
)

data class local_names(
    var fa: String
)