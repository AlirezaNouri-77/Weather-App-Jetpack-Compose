package com.shermanrex.weatherapp.jetpack.weatherapp.Model

import com.shermanrex.weatherapp.jetpack.weatherapp.R

data class WeatherIconModel(
    val IconList:List<Icon> = listIcon
)

var listIcon:List<Icon> = listOf(
    Icon(200, R.drawable.a01d , R.drawable.a01n),
    Icon(201, R.drawable.t02d , R.drawable.t02n),
    Icon(202, R.drawable.t03d , R.drawable.t03n),
    Icon(230, R.drawable.t04d , R.drawable.t04n),
    Icon(232, R.drawable.t04d , R.drawable.t04n),
    Icon(233, R.drawable.t05d , R.drawable.t05n),
    Icon(300, R.drawable.d01d , R.drawable.d01n),
    Icon(301, R.drawable.d02d , R.drawable.d02n),
    Icon(302, R.drawable.d03d , R.drawable.d03n),
    Icon(500, R.drawable.r01d , R.drawable.r01n),
    Icon(501, R.drawable.r02d , R.drawable.r02n),
    Icon(502, R.drawable.r03d , R.drawable.r03n),
    Icon(511, R.drawable.f01d , R.drawable.f01n),
    Icon(520, R.drawable.r04d , R.drawable.r04n),
    Icon(521, R.drawable.r05d , R.drawable.r05n),
    Icon(522, R.drawable.r06d , R.drawable.r06n),
    Icon(600, R.drawable.s01d , R.drawable.s01n),
    Icon(601, R.drawable.s02d , R.drawable.s02n),
    Icon(602, R.drawable.s03d , R.drawable.s03n),
    Icon(610, R.drawable.s04d , R.drawable.s04n),
    Icon(611, R.drawable.s05d , R.drawable.s05n),
    Icon(612, R.drawable.s05d , R.drawable.s05n),
    Icon(621, R.drawable.s01d , R.drawable.s01n),
    Icon(622, R.drawable.s02d , R.drawable.s02n),
    Icon(623, R.drawable.s06d , R.drawable.s06n),
    Icon(700, R.drawable.a01d , R.drawable.a01n),
    Icon(711, R.drawable.a02d , R.drawable.a02n),
    Icon(721, R.drawable.a03d , R.drawable.a03n),
    Icon(731, R.drawable.a04d , R.drawable.a04n),
    Icon(741, R.drawable.a05d , R.drawable.a05n),
    Icon(751, R.drawable.a06d , R.drawable.a06n),
    Icon(800, R.drawable.c01d , R.drawable.c01n),
    Icon(801, R.drawable.c02d , R.drawable.c02n),
    Icon(802, R.drawable.c02d , R.drawable.c02n),
    Icon(803, R.drawable.c03d , R.drawable.c03n),
    Icon(804, R.drawable.c04d , R.drawable.c04n),
    Icon(900, R.drawable.u00d , R.drawable.u00n),
)

data class Icon(
    var Code:Int,
    var IconDay:Int,
    var IconNight:Int
)
