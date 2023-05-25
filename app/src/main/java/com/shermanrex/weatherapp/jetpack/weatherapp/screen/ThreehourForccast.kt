package com.shermanrex.weatherapp.jetpack.weatherapp.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shermanrex.weatherapp.jetpack.weatherapp.models.ThreeHourWeatherModel
import com.shermanrex.weatherapp.jetpack.weatherapp.models.datalist
import com.shermanrex.weatherapp.jetpack.weatherapp.util.WeatherIconFinder
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun ThreehourForccast(data: ThreeHourWeatherModel) {
    Card(
        Modifier.padding(5.dp) ,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.4F)
        )
    ) {
        LazyRow(Modifier.fillMaxWidth() , content = {
            itemsIndexed(data.list) { index , item ->
                ThreehourForccastList(data.list[index])
            }
        })
    }
}

@Composable
fun ThreehourForccastList(datalist: datalist) {

    val weathericon by lazy {
        WeatherIconFinder()
    }

    val isDay: Boolean = datalist.sys.pod == "d"

    Column(
        horizontalAlignment = Alignment.CenterHorizontally ,
        verticalArrangement = Arrangement.Center ,
        modifier = Modifier.padding(10.dp)
    ) {
        Text(
            text = timeStampToDate(datalist.dt.toLong()) ,
            fontSize = 12.sp ,
            fontWeight = FontWeight.Light ,
            color = MaterialTheme.colorScheme.onPrimary
        )
        Image(
            painter = painterResource(
                id = weathericon.getIcon(
                    Code = datalist.weather[0].id ,
                    isDay
                )
            ) ,
            contentDescription = "" ,
            Modifier.size(25.dp)
        )
        Text(
            text = datalist.main.temp.toString()+"°" ,
            fontSize = 15.sp ,
            fontWeight = FontWeight.Light ,
            color = MaterialTheme.colorScheme.onPrimary
        )

    }
}

fun timeStampToDate(timetamp: Long): String {
    val dataformat = DateTimeFormatter.ofPattern("dd LLLL HH:mm").withZone(ZoneId.of("GMT"))
        .format(Instant.ofEpochSecond(timetamp))
    return dataformat.toString()
}