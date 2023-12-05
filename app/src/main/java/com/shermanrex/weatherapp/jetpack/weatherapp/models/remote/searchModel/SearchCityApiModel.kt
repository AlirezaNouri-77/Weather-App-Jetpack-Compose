package com.shermanrex.weatherapp.jetpack.weatherapp.models.remote.searchModel

import com.google.gson.annotations.SerializedName

class SearchCityApiModel : ArrayList<SearchCityModel>()

data class SearchCityModel(
  @SerializedName("name") var name: String,
  @SerializedName("lat") var lat: String,
  @SerializedName("lon") var lon: String,
  @SerializedName("country") var country: String,
  @SerializedName("state") var state: String,
  @SerializedName("local_names") var localNames: LocalName,
) {
  data class LocalName(
	var fa: String,
  )
}

