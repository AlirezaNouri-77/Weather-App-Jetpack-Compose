package com.shermanrex.weatherapp.jetpack.weatherapp.util

import android.icu.util.LocaleData
import android.util.Log
import java.time.Instant
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

class NameofWeek {

    fun convert(timestamp:Long):String{
        var date = Date.from(Instant.ofEpochMilli(timestamp))
       return DateTimeFormatter.ofPattern(
            "yyyy MM dd"
        ).format(date.toInstant())

    }


    fun convertToDayName(timestamp: Long): String {
        return DateTimeFormatter.ofPattern(
            "dd/MM/yyyy" ,
            Locale.getDefault()
        ).format(Instant.ofEpochMilli(timestamp))
    }
}