package com.shermanrex.weatherapp.jetpack.weatherapp.util

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class timeStampFormatter:timeStampFormmaterInterface {
    override fun timeStampFormatterThreehour(timestamp: Long): String {
        return DateTimeFormatter.ofPattern("dd LLLL HH:mm").withZone(ZoneId.of("GMT"))
            .format(Instant.ofEpochSecond(timestamp)).toString()
    }

    override fun timeStampFormatterSevenDay(timestamp: Long): String {
        return DateTimeFormatter.ofPattern("dd LLLL").withZone(ZoneId.of("GMT"))
            .format(Instant.ofEpochSecond(timestamp)).toString()
    }

}

interface timeStampFormmaterInterface {
    fun timeStampFormatterThreehour(timestamp:Long):String
    fun timeStampFormatterSevenDay(timestamp:Long):String
}