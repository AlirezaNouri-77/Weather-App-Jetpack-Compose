package com.shermanrex.weatherapp.jetpack.weatherapp.screens.weatherPage.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shermanrex.weatherapp.jetpack.weatherapp.R
import com.shermanrex.weatherapp.jetpack.weatherapp.models.remote.weatherModels.CurrentWeatherModel
import kotlin.math.roundToInt

@Composable
fun TodayWeatherDetailSection(data : CurrentWeatherModel) {

    val feelslike = if (data.main.feelsLike > data.main.temp) "Feel hotter" else "Wind make it feel cooler"

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
                    value = data.main.feelsLike.roundToInt()
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


@Composable
fun DetailGridItem(
    icon: Int,
    iconDescription: String,
    value: String,
    Description: String = "",
    textColor: Color = MaterialTheme.colorScheme.onPrimary,
    backgroundColor: Color = MaterialTheme.colorScheme.onBackground
) {
    Card(
        colors = CardDefaults.cardColors(
            backgroundColor
        ),
        modifier = Modifier
            .wrapContentSize()
            .padding(5.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            
            ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(10.dp)
            ) {
                Image(
                    painter = painterResource(id = icon),
                    contentDescription = "",
                    modifier = Modifier
                        .size(25.dp)
                        .wrapContentSize(),
                    colorFilter = ColorFilter.tint(textColor)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = iconDescription, fontWeight = FontWeight.Medium, fontSize = 15.sp,
                    color = textColor,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
            Text(
                text = value,
                Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium,
                color = textColor
            )
            
            Text(
                text = Description,
                Modifier
                    .padding(5.dp)
                    .fillMaxWidth(),
                fontSize = 14.sp,
                fontWeight = FontWeight.Light,
                color = textColor
            )
            
        }
    }
    
}