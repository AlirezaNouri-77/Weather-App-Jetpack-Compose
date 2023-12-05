package com.shermanrex.weatherapp.jetpack.weatherapp.screens.weatherPage.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shermanrex.weatherapp.jetpack.weatherapp.Constant.Constant.CELSIUS_SYMBOL
import com.shermanrex.weatherapp.jetpack.weatherapp.Constant.Constant.FAHRENHEIT_SYMBOL
import com.shermanrex.weatherapp.jetpack.weatherapp.models.remote.weatherModels.CurrentWeatherModel
import com.shermanrex.weatherapp.jetpack.weatherapp.util.convertCodeToName
import kotlin.math.roundToInt

@Composable
fun TodayWeatherSection(
  data: CurrentWeatherModel,
  weatherUnitDataStore: String,
  onClickDegree: (String) -> Unit,
  degreeUnit: String = if (weatherUnitDataStore == "METRIC") CELSIUS_SYMBOL else FAHRENHEIT_SYMBOL
) {
  
  Box(Modifier.padding(bottom = 10.dp, top = 10.dp)) {
	Column(
	  verticalArrangement = Arrangement.Center,
	  horizontalAlignment = Alignment.CenterHorizontally,
	) {
	  
	  UnitDegreeSymbol(
		currentDegree = weatherUnitDataStore,
		onclick = {
		  when (weatherUnitDataStore) {
			"METRIC" -> {
			  onClickDegree.invoke("IMPERIAL")
			}
			
			"IMPERIAL" -> {
			  onClickDegree.invoke("METRIC")
			}
			
			else -> {}
		  }
		})
	  
	  Text(
		text = data.name,
		fontWeight = FontWeight.Light,
		fontSize = 24.sp,
		color = Color.White
	  )
	  
	  Text(
		text = data.sys.country.convertCodeToName(),
		fontWeight = FontWeight.Light,
		fontSize = 16.sp,
		color = Color.White,
	  )
	  
	  Text(text = buildAnnotatedString {
		withStyle(SpanStyle(
		  fontSize = 54.sp,
		  fontWeight = FontWeight.SemiBold,
		  color = Color.White
		), block = {
		  append(data.main.temp.roundToInt().toString() + degreeUnit)
		})
	  }, Modifier.padding(5.dp))
	  
	  Text(
		text = data.weather[0].description,
		fontWeight = FontWeight.Light,
		fontSize = 18.sp,
		color = Color.White
	  )
	  
	  Row(
		horizontalArrangement = Arrangement.Center,
		verticalAlignment = Alignment.CenterVertically
	  ) {
		Text(text = buildAnnotatedString {
		  withStyle(SpanStyle(
			fontSize = 15.sp,
			fontWeight = FontWeight.Light,
			color = Color.White
		  ), block = {
			append("H: ${data.main.tempMax.roundToInt()}°")
		  })
		})
		
		Spacer(modifier = Modifier.width(15.dp))
		
		Text(text = buildAnnotatedString {
		  withStyle(SpanStyle(
			fontSize = 15.sp,
			fontWeight = FontWeight.Light,
			color = Color.White
		  ), block = {
			append("L: ${data.main.tempMin.roundToInt()}°")
		  })
		})
	  }
	}
  }
}



