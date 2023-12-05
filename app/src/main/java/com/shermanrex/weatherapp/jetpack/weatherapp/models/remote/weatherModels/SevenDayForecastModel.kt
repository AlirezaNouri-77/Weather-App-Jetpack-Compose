package com.shermanrex.weatherapp.jetpack.weatherapp.models.remote.weatherModels

import com.google.gson.annotations.SerializedName

data class SevenDayForecastModel(
  @SerializedName("city_name") var cityName: String,
  @SerializedName("country_code") var countryCode: String,
  @SerializedName("data") var `data`: List<Data>,
  @SerializedName("lat") var lat: Double,
  @SerializedName("lon") var lon: Double,
  @SerializedName("state_code") var stateCode: String,
  @SerializedName("timezone") var timezone: String
) {
  data class Data(
	@SerializedName("app_max_temp") var appMaxTemp: Double,
	@SerializedName("app_min_temp") var appMinTemp: Double,
	@SerializedName("clouds") var clouds: Int,
	@SerializedName("clouds_hi") var cloudsHi: Int,
	@SerializedName("clouds_low") var cloudsLow: Int,
	@SerializedName("clouds_mid") var cloudsMid: Int,
	@SerializedName("datetime") var datetime: String,
	@SerializedName("dewpt") var dewpt: Double,
	@SerializedName("high_temp") var highTemp: Double,
	@SerializedName("low_temp") var lowTemp: Double,
	@SerializedName("max_dhi") var maxDhi: Any?,
	@SerializedName("max_temp") var maxTemp: Double,
	@SerializedName("min_temp") var minTemp: Double,
	@SerializedName("moon_phase") var moonPhase: Double,
	@SerializedName("moon_phase_lunation") var moonPhaseLunation: Double,
	@SerializedName("moonrise_ts") var moonriseTs: Int,
	@SerializedName("moonset_ts") var moonsetTs: Int,
	@SerializedName("ozone") var ozone: Double,
	@SerializedName("pop") var pop: Int,
	@SerializedName("precip") var precip: Double,
	@SerializedName("pres") var pres: Double,
	@SerializedName("rh") var rh: Int,
	@SerializedName("slp") var slp: Double,
	@SerializedName("snow") var snow: Double,
	@SerializedName("snow_depth") var snowDepth: Double,
	@SerializedName("sunrise_ts") var sunriseTs: Int,
	@SerializedName("sunset_ts") var sunsetTs: Int,
	@SerializedName("temp") var temp: Double,
	@SerializedName("ts") var ts: Int,
	@SerializedName("uv") var uv: Double,
	@SerializedName("valid_date") var validDate: String,
	@SerializedName("vis") var vis: Double,
	@SerializedName("weather") var weather: Weather,
	@SerializedName("wind_cdir") var windCdir: String,
	@SerializedName("wind_cdir_full") var windCdirFull: String,
	@SerializedName("wind_dir") var windDir: Int,
	@SerializedName("wind_gust_spd") var windGustSpd: Double,
	@SerializedName("wind_spd") var windSpd: Double
  ) {
	data class Weather(
	  @SerializedName("code") var code: Int,
	  @SerializedName("description") var description: String,
	  @SerializedName("icon") var icon: String
	)
  }
}