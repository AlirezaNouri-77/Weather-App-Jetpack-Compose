package com.shermanrex.weatherapp.jetpack.weatherapp.screens.weatherPage.component

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
import com.shermanrex.weatherapp.jetpack.weatherapp.Constant.Constant.DEGREE_SYMBOL
import com.shermanrex.weatherapp.jetpack.weatherapp.models.remote.weatherModels.ThreeHourWeatherModel
import com.shermanrex.weatherapp.jetpack.weatherapp.util.WeatherIconFinder
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

@Composable
fun ThreeHourForecastSection(data: ThreeHourWeatherModel) {
  
  Card(
	Modifier.padding(top = 5.dp, start = 10.dp, end = 10.dp),
	colors = CardDefaults.cardColors(
	  containerColor = MaterialTheme.colorScheme.onBackground
	)
  ) {
	Text(
	  text = "3 Hour forecast",
	  Modifier.padding(top = 10.dp, bottom = 5.dp, start = 10.dp, end = 10.dp),
	  color = MaterialTheme.colorScheme.onPrimary,
	  fontWeight = FontWeight.SemiBold,
	  fontSize = 18.sp
	)
	
	LazyRow(Modifier.fillMaxWidth(), content = {
	  itemsIndexed(data.list) { index, item ->
		ThreehourForecastList(data.list[index])
	  }
	})
  }
}

@Composable
fun ThreehourForecastList(datalist: ThreeHourWeatherModel.WeatherData) {
  
  val weatherIcon by lazy {
	WeatherIconFinder()
  }
  
  val isDay: Boolean = datalist.sys.pod == "d"
  
  Column(
	horizontalAlignment = Alignment.CenterHorizontally,
	verticalArrangement = Arrangement.Center,
	modifier = Modifier.padding(10.dp)
  ) {
	
	Text(
	  text = timeStampToDate(datalist.dt.toLong()),
	  fontSize = 12.sp,
	  fontWeight = FontWeight.Light,
	  color = MaterialTheme.colorScheme.onPrimary,
	  textAlign = TextAlign.Center
	)
	
	Image(
	  painter = painterResource(
		id = weatherIcon.getIconResource(
		  Code = datalist.weather[0].id,
		  isDay
		)
	  ),
	  contentDescription = "",
	  Modifier.size(30.dp)
	)
	
	Text(
	  text = datalist.main.temp.roundToInt().toString() + DEGREE_SYMBOL,
	  fontSize = 17.sp,
	  fontWeight = FontWeight.Light,
	  color = MaterialTheme.colorScheme.onPrimary,
	  modifier = Modifier.padding(5.dp)
	)
	
  }
}

fun timeStampToDate(timestamp: Long): String {
  val dateFormat = DateTimeFormatter.ofPattern("dd LLL").withZone(ZoneId.of("GMT"))
	.format(Instant.ofEpochSecond(timestamp))
  val timeFormat = DateTimeFormatter.ofPattern("HH:mm").withZone(ZoneId.of("GMT"))
	.format(Instant.ofEpochSecond(timestamp))
  return dateFormat + "\n" + timeFormat
}