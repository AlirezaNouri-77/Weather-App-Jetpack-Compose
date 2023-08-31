package com.shermanrex.weatherapp.jetpack.weatherapp.screenComponent

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shermanrex.weatherapp.jetpack.weatherapp.models.SealedResponseResultModel
import com.shermanrex.weatherapp.jetpack.weatherapp.models.ThreeHourWeatherModel
import com.shermanrex.weatherapp.jetpack.weatherapp.models.datalist
import com.shermanrex.weatherapp.jetpack.weatherapp.util.WeatherIconFinder
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

@Composable
fun ThreehourForccast(data: ThreeHourWeatherModel) {

    Card(
        Modifier.padding(top = 5.dp  , start = 10.dp, end = 10.dp) ,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onBackground
          //  containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.3F)
        )
    ) {
        Text(
            text = "3 Hour forecast" ,
            Modifier.padding(top = 10.dp , bottom = 5.dp , start = 10.dp , end = 10.dp) ,
            color = MaterialTheme.colorScheme.onPrimary,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp
        )

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
            color = MaterialTheme.colorScheme.onPrimary,
            textAlign = TextAlign.Center
        )

        Image(
            painter = painterResource(
                id = weathericon.getIcon(
                    Code = datalist.weather[0].id ,
                    isDay
                )
            ) ,
            contentDescription = "" ,
            Modifier.size(30.dp)
        )

        Text(
            text = datalist.main.temp.roundToInt().toString()+"°" ,
            fontSize = 17.sp ,
            fontWeight = FontWeight.Light ,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.padding(5.dp)
        )

    }
}

fun timeStampToDate(timetamp: Long): String {
    val dateFormat = DateTimeFormatter.ofPattern("dd LLLL").withZone(ZoneId.of("GMT"))
        .format(Instant.ofEpochSecond(timetamp))
    val timeFormat = DateTimeFormatter.ofPattern("HH:mm").withZone(ZoneId.of("GMT"))
        .format(Instant.ofEpochSecond(timetamp))
    return dateFormat + "\n" + timeFormat
}