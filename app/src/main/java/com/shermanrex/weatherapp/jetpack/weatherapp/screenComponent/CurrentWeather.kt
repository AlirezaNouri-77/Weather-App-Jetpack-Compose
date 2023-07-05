package com.shermanrex.weatherapp.jetpack.weatherapp.screenComponent

import androidx.compose.foundation.clickable
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
import com.shermanrex.weatherapp.jetpack.weatherapp.models.CurrentWeatherModel
import java.util.Locale
import kotlin.math.roundToInt

@Composable
fun CurrentWeather(
    data: CurrentWeatherModel,
    weatherUnitDataStore: String,
    onClickDegree: (String) -> Unit,
    onMoreDetail: () -> Unit
) {

    val degreeUnit = if (weatherUnitDataStore == "METRIC") CELSIUS_SYMBOL else FAHRENHEIT_SYMBOL

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
                text = countryCodeToName(data.sys.country),
                fontWeight = FontWeight.Light,
                fontSize = 16.sp,
                color = Color.White,
            )

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "Lat: ${data.coord.lat}",
                    color = Color.White,
                    fontSize = 12.sp
                )

                Spacer(modifier = Modifier.width(15.dp))

                Text(
                    text = "Lon: ${data.coord.lon}",
                    color = Color.White,
                    fontSize = 12.sp
                )

            }

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
                        append("H: ${data.main.temp_max.roundToInt()}°")
                    })
                })

                Spacer(modifier = Modifier.width(15.dp))

                Text(text = buildAnnotatedString {
                    withStyle(SpanStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Light,
                        color = Color.White
                    ), block = {
                        append("L: ${data.main.temp_min.roundToInt()}°")
                    })
                })

            }

            Text(
                text =  "More Detail" ,
                color = Color.White ,
                fontSize = 15.sp ,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.clickable {
                    onMoreDetail.invoke()
                }.padding(5.dp)
            )
        }
    }
}

fun countryCodeToName(CountyCode:String):String{
    return Locale("EN" , CountyCode ).displayCountry.toString()
}


