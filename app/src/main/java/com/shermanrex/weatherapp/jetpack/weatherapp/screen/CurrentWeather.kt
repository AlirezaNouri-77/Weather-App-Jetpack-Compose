package com.shermanrex.weatherapp.jetpack.weatherapp.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shermanrex.weatherapp.jetpack.weatherapp.R
import com.shermanrex.weatherapp.jetpack.weatherapp.models.CurrentWeatherModel

@Composable
fun CurrentWeather(data: CurrentWeatherModel) {

    var showDetail by rememberSaveable {
        mutableStateOf(false)
    }

    Box(Modifier.padding(bottom = 15.dp , top = 10.dp)) {
        Column(
            verticalArrangement = Arrangement.Center ,
            horizontalAlignment = Alignment.CenterHorizontally ,

            ) {
            Icon(
                imageVector = Icons.Default.LocationOn ,
                contentDescription = "" ,
                tint = MaterialTheme.colorScheme.onPrimary
            )
            Text(
                text = data.name ,
                fontWeight = FontWeight.Light ,
                fontSize = 24.sp ,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Row(
                horizontalArrangement = Arrangement.Center ,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Lat: ${data.coord.lat}" ,
                    color = MaterialTheme.colorScheme.onPrimary ,
                    fontSize = 13.sp
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "Lon: ${data.coord.lon}" ,
                    color = MaterialTheme.colorScheme.onPrimary ,
                    fontSize = 13.sp
                )
            }
            Text(text = buildAnnotatedString {
                withStyle(
                    SpanStyle(
                        fontSize = 48.sp ,
                        fontWeight = FontWeight.SemiBold ,

                        color = MaterialTheme.colorScheme.onPrimary
                    ) ,
                    block = {
                        append(data.main.temp.toString() + "°")
                    }
                )
            } , Modifier.padding(10.dp))
            Text(
                text = data.weather[0].description ,
                fontWeight = FontWeight.Light ,
                fontSize = 18.sp ,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Row(
                horizontalArrangement = Arrangement.Center ,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "H: " ,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Text(text = buildAnnotatedString {
                    withStyle(
                        SpanStyle(
                            fontSize = 16.sp ,
                            fontWeight = FontWeight.Light ,
                            color = MaterialTheme.colorScheme.onPrimary
                        ) ,
                        block = {
                            append(data.main.temp_max.toString() + "°")
                        }
                    )
                })
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "L: " ,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Text(text = buildAnnotatedString {
                    withStyle(
                        SpanStyle(
                            fontSize = 16.sp ,
                            fontWeight = FontWeight.Light ,
                            color = MaterialTheme.colorScheme.onPrimary
                        ) ,
                        block = {
                            append(data.main.temp_min.toString() + "°")
                        }
                    )
                })
            }
            Row(
                verticalAlignment = Alignment.CenterVertically ,
                horizontalArrangement = Arrangement.Center ,
                modifier = Modifier
                    .padding(top = 10.dp)
                    .clickable {
                        showDetail = !showDetail
                    }
            ) {
                Text(
                    text = if (!showDetail) "Show more detail" else "Collapse" ,
                    color = MaterialTheme.colorScheme.onPrimary ,
                    fontSize = 16.sp ,
                    fontWeight = FontWeight.Medium
                )
                Icon(
                    imageVector = if (!showDetail) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp ,
                    contentDescription = "" ,
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
            AnimatedVisibility(visible = showDetail) {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp) ,
                    content = {
                        DetailCurrent(
                            icon = R.drawable.icon_pressure ,
                            description = "Pressure" ,
                            value = data.main.pressure.toString()
                        )
                        DetailCurrent(
                            icon = R.drawable.icon_pressure ,
                            description = "Feels like" ,
                            value = data.main.feels_like.toString() ,
                            valuedescription = if (data.main.temp > data.main.feels_like) "Wind make it feel cooler" else ""
                        )
                        DetailCurrent(
                            icon = R.drawable.icon_pressure ,
                            description = "Humidity" ,
                            value = data.main.humidity.toString()
                        )
                        DetailCurrent(
                            icon = R.drawable.icon_pressure ,
                            description = "Sea level" ,
                            value = data.main.sea_level.toString()
                        )
                        DetailCurrent(
                            icon = R.drawable.icon_visibility ,
                            description = "Visibility" ,
                            value = data.visibility.toString()
                        )
                        DetailCurrent(
                            icon = R.drawable.icon_wind_speed ,
                            description = "Wind speed" ,
                            value = data.wind.speed.toString()
                        )
                        DetailCurrent(
                            icon = R.drawable.icon_cloud ,
                            description = "Cloude" ,
                            value = data.clouds.toString()
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun DetailCurrent(
    icon: Int ,
    description: String ,
    value: String ,
    valuedescription: String = ""
) {
    Column(
        verticalArrangement = Arrangement.Center ,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically ,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = icon) ,
                contentDescription = "" ,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary)
            )
            Text(
                text = description ,
                color = MaterialTheme.colorScheme.onPrimary ,
                fontWeight = FontWeight.Medium ,
                fontSize = 18.sp
            )
        }
        Text(
            text = "$value  $valuedescription" ,
            color = MaterialTheme.colorScheme.onPrimary ,
            fontWeight = FontWeight.Light ,
            fontSize = 15.sp
        )
    }
}

@Composable
fun DetailGird() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 100.dp)
            .padding(10.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center ,
            horizontalAlignment = Alignment.CenterHorizontally ,
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                horizontalArrangement = Arrangement.Start
            ) {
                Image(
                    painter = painterResource(id = R.drawable.icon_pressure) ,
                    contentDescription = "" ,
                    Modifier
                        .size(20.dp)
                        .padding(start = 5.dp , top = 5.dp) ,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary) ,
                    contentScale = ContentScale.Fit
                )
                Text(
                    text = "Pressure" ,
                    Modifier.padding(start = 10.dp , end = 5.dp) ,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
            Text(
                text = "10" ,
                color = MaterialTheme.colorScheme.onPrimary ,
                fontSize = 16.sp ,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}


@Preview
@Composable
fun previewDetailGird() {
    MaterialTheme {
        LazyVerticalGrid(columns = GridCells.Fixed(2) , content = {
            repeat(10) {
                item {
                    DetailGird()
                }
            }
        })

    }
}
