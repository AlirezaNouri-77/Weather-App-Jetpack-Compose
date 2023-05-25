package com.shermanrex.weatherapp.jetpack.weatherapp.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shermanrex.weatherapp.jetpack.weatherapp.R
import com.shermanrex.weatherapp.jetpack.weatherapp.models.ForecastData
import com.shermanrex.weatherapp.jetpack.weatherapp.models.SevenDayForecastModel
import com.shermanrex.weatherapp.jetpack.weatherapp.ui.theme.WeatherAppTheme
import com.shermanrex.weatherapp.jetpack.weatherapp.util.WeatherIconFinder

@Composable
fun SevenDayForecast(data: SevenDayForecastModel) {

    val WeatherIconFinder by lazy {
        WeatherIconFinder()
    }

    Card(
        Modifier.padding(5.dp) ,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.4F)
        )
    ) {
        Text(
            text = "7-day forecast" ,
            Modifier.padding(top = 10.dp , bottom = 4.dp , start = 10.dp , end = 10.dp) ,
            color = MaterialTheme.colorScheme.onPrimary

        )
        LazyColumn(
            Modifier
                .heightIn(max = 900.dp)
                .padding(top = 10.dp) ,
        ) {
            itemsIndexed(data.data) { index , item ->
                if (index!=0) {
                    SevenDayForeCastListItem(item , WeatherIconFinder)
                }
            }
        }
    }

}

@Composable
private fun SevenDayForeCastListItem(
    forecastData: ForecastData ,
    WeatherIconFinder: WeatherIconFinder
) {

    var showDetail by rememberSaveable {
        mutableStateOf(false)
    }

    Column(
        verticalArrangement = Arrangement.Center ,
        modifier = Modifier.clickable {
            showDetail = !showDetail
        }
    ) {
        Row(
            horizontalArrangement = Arrangement.Center ,
            verticalAlignment = Alignment.CenterVertically ,
        ) {
            Row(
                Modifier
                    .weight(1F) ,
                verticalAlignment = Alignment.CenterVertically ,
                horizontalArrangement = Arrangement.Start
            ) {
                Image(
                    painter = painterResource(
                        id = WeatherIconFinder.getIcon(
                            forecastData.weather.code ,
                            forecastData.weather.icon.contains("n")
                        )
                    ) ,
                    contentDescription = "" ,
                    Modifier
                        .size(40.dp)
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally ,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = forecastData.weather.description ,
                        fontSize = 12.sp ,
                        fontWeight = FontWeight.Light ,
                        color = MaterialTheme.colorScheme.onPrimary ,
                    )
                    Text(
                        text = forecastData.valid_date ,
                        fontSize = 10.sp ,
                        fontWeight = FontWeight.Light ,
                        color = MaterialTheme.colorScheme.onPrimary ,
                    )
                }

            }

            Column(
                Modifier
                    .weight(0.5F) ,
                verticalArrangement = Arrangement.Center ,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(text = buildAnnotatedString {
                    withStyle(
                        SpanStyle(
                            fontSize = 24.sp ,
                            fontWeight = FontWeight.SemiBold ,
                            color = MaterialTheme.colorScheme.onPrimary ,
                        ) ,
                        block = {
                            append(forecastData.temp.toString() + "°")
                        }
                    )
                })
            }

            Column(
                Modifier
                    .fillMaxWidth()
                    .weight(1F) ,
                horizontalAlignment = Alignment.CenterHorizontally ,
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center ,
                    verticalAlignment = Alignment.CenterVertically ,
                    modifier = Modifier.padding(start = 5.dp)
                ) {

                    Icon(
                        imageVector = Icons.Default.KeyboardArrowUp ,
                        contentDescription = "" ,
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                    Text(
                        modifier = Modifier.align(alignment = Alignment.CenterVertically) ,
                        text = forecastData.max_temp.toString() ,
                        fontWeight = FontWeight.Light ,
                        color = MaterialTheme.colorScheme.onPrimary ,
                        fontSize = 12.sp
                    )
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown ,
                        contentDescription = "" ,
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                    Text(
                        modifier = Modifier
                            .align(alignment = Alignment.CenterVertically) ,
                        text = forecastData.min_temp.toString() ,
                        fontWeight = FontWeight.Light ,
                        color = MaterialTheme.colorScheme.onPrimary ,
                        fontSize = 12.sp
                    )
                }
            }

        }
        AnimatedVisibility(visible = showDetail) {
            LazyVerticalGrid(
                verticalArrangement = Arrangement.Center ,
                horizontalArrangement = Arrangement.Center ,
                modifier = Modifier.heightIn(max = 900.dp) ,
                columns = GridCells.Fixed(2) ,
                content = {
                    repeat(10) {
                        item {
                            SevenDayForecastDetail(
                                imageInt = R.drawable.precipitation ,
                                subject = "precipitation" ,
                                desc = "10%"
                            )
                        }
                    }
                })
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally ,
            verticalArrangement = Arrangement.Center ,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = if (!showDetail) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp ,
                contentDescription = "" ,
                modifier = Modifier.size(15.dp) ,
                tint = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5F)
            )
        }

    }
}

@Composable
fun SevenDayForecastDetail(imageInt: Int , subject: String , desc: String) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally ,
        verticalArrangement = Arrangement.Center ,
        modifier = Modifier.padding(10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically ,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = imageInt) ,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary) ,
                contentDescription = "" ,
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "$subject " ,
                fontSize = 14.sp ,
                color = MaterialTheme.colorScheme.onPrimary ,
                modifier = Modifier.padding(10.dp)
            )
        }
        Text(
            text = desc , fontSize = 16.sp , color = MaterialTheme.colorScheme.onPrimary ,
            modifier = Modifier.padding(5.dp)
        )
    }
}

@Composable
@Preview("Light" , showBackground = true)
@Preview(name = "Dark" , uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
fun preview() {
    WeatherAppTheme {
        SevenDayForecastDetail(
            imageInt = R.drawable.precipitation , subject = "precipitation" ,
            desc = "10%"
        )
    }
}
