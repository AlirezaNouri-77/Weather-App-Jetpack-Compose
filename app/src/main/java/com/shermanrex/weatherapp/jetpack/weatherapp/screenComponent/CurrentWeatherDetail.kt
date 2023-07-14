package com.shermanrex.weatherapp.jetpack.weatherapp.screenComponent

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shermanrex.weatherapp.jetpack.weatherapp.R
import com.shermanrex.weatherapp.jetpack.weatherapp.models.CurrentWeatherModel
import kotlin.math.roundToInt

@Composable
fun CurrentWeatherDetial(data : CurrentWeatherModel) {

    val feelslike = if (data.main.feels_like > data.main.temp) "Feel hotter" else "Wind make it feel cooler"

    LazyVerticalGrid(columns = GridCells.Fixed(2),
        Modifier.heightIn(max = 1000.dp),
        contentPadding = PaddingValues(10.dp),
        horizontalArrangement = Arrangement.Center,
        verticalArrangement = Arrangement.Center,
        content = {
            item {
                DetailGridItem(
                    icon = R.drawable.icon_pressure,
                    iconDescription = "Feels Like",
                    value = data.main.feels_like.roundToInt()
                        .toString() + "°",
                    Description = feelslike
                )
            }
            item {
                DetailGridItem(
                    icon = R.drawable.icon_pressure,
                    iconDescription = "Pressure",
                    value = data.main.pressure.toString(),
                )
            }
            item {
                DetailGridItem(
                    icon = R.drawable.icon_cloud,
                    iconDescription = "Cloud",
                    value = data.clouds.all.toString() + " %",
                )
            }
            item {
                DetailGridItem(
                    icon = R.drawable.icon_visibility,
                    iconDescription = "Visibility",
                    value = (data.visibility).div(1000).toString() +"KM",
                )
            }
            item {
                DetailGridItem(
                    icon = R.drawable.icon_humidity,
                    iconDescription = "Humidity",
                    value = data.main.humidity.toString() + " %",
                )
            }
            item {
                DetailGridItem(
                    icon = R.drawable.icon_wind_speed,
                    iconDescription = "Wind Speed",
                    value = data.wind.speed.roundToInt().toString()+"m/s"
                        .toString(),
                )
            }
            item {
                DetailGridItem(
                    icon = R.drawable.icon_winddirection,
                    iconDescription = "Wind Direction",
                    value = data.wind.deg.toString(),
                )
            }
        })
}