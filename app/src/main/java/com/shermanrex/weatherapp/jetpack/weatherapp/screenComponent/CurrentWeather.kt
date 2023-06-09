package com.shermanrex.weatherapp.jetpack.weatherapp.screenComponent

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shermanrex.weatherapp.jetpack.weatherapp.Constant.Constant.CELSIUS_SYMBOL
import com.shermanrex.weatherapp.jetpack.weatherapp.Constant.Constant.FAHRENHEIT_SYMBOL
import com.shermanrex.weatherapp.jetpack.weatherapp.R
import com.shermanrex.weatherapp.jetpack.weatherapp.models.CurrentWeatherModel
import kotlin.math.roundToInt

@Composable
fun CurrentWeather(
    data: CurrentWeatherModel,
    weatherUnitDataStore: String,
    onClickDegree: (String) -> Unit,
    onMoreDetail: () -> Unit
) {

    var showDetail by rememberSaveable {
        mutableStateOf(false)
    }

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
                color = MaterialTheme.colorScheme.onPrimary
            )
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "Lat: ${data.coord.lat}",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 12.sp
                )

                Spacer(modifier = Modifier.width(15.dp))

                Text(
                    text = "Lon: ${data.coord.lon}",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 12.sp
                )

            }

            Text(text = buildAnnotatedString {
                withStyle(SpanStyle(
                    fontSize = 54.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onPrimary
                ), block = {
                    append(data.main.temp.roundToInt().toString() + degreeUnit)
                })
            }, Modifier.padding(5.dp))

            Text(
                text = data.weather[0].description,
                fontWeight = FontWeight.Light,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onPrimary
            )

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = buildAnnotatedString {
                    withStyle(SpanStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Light,
                        color = MaterialTheme.colorScheme.onPrimary
                    ), block = {
                        append("H: ${data.main.temp_max.roundToInt()}°")
                    })
                })

                Spacer(modifier = Modifier.width(15.dp))

                Text(text = buildAnnotatedString {
                    withStyle(SpanStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Light,
                        color = MaterialTheme.colorScheme.onPrimary
                    ), block = {
                        append("L: ${data.main.temp_min.roundToInt()}°")
                    })
                })

            }

            Text(
                text =  "More Detail" ,
                color = MaterialTheme.colorScheme.onPrimary ,
                fontSize = 15.sp ,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.clickable {
                    onMoreDetail.invoke()
                }.padding(5.dp)
            )
        }
    }
}

//@Composable
//fun DetailCurrent(
//    icon: Int,
//    description: String,
//    value: String,
//    valuedescription: String = ""
//) {
//    Column(
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.Center
//        ) {
//            Image(
//                painter = painterResource(id = icon),
//                contentDescription = "",
//                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary)
//            )
//            Text(
//                text = description,
//                color = MaterialTheme.colorScheme.onPrimary,
//                fontWeight = FontWeight.Medium,
//                fontSize = 18.sp
//            )
//        }
//        Text(
//            text = "$value  $valuedescription",
//            color = MaterialTheme.colorScheme.onPrimary,
//            fontWeight = FontWeight.Light,
//            fontSize = 15.sp
//        )
//    }
//}
//
//@Composable
//fun DetailGird() {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .heightIn(min = 100.dp)
//            .padding(10.dp)
//    ) {
//        Column(
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally,
//            modifier = Modifier.fillMaxSize()
//        ) {
//            Row(
//                horizontalArrangement = Arrangement.Start
//            ) {
//                Image(
//                    painter = painterResource(id = R.drawable.icon_pressure),
//                    contentDescription = "",
//                    Modifier
//                        .size(20.dp)
//                        .padding(start = 5.dp, top = 5.dp),
//                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary),
//                    contentScale = ContentScale.Fit
//                )
//                Text(
//                    text = "Pressure",
//                    Modifier.padding(start = 10.dp, end = 5.dp),
//                    color = MaterialTheme.colorScheme.onPrimary
//                )
//            }
//            Text(
//                text = "10",
//                color = MaterialTheme.colorScheme.onPrimary,
//                fontSize = 16.sp,
//                fontWeight = FontWeight.SemiBold
//            )
//        }
//    }
//}

