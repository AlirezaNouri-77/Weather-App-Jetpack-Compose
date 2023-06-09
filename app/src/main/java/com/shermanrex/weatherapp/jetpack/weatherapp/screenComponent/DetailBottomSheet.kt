package com.shermanrex.weatherapp.jetpack.weatherapp.screenComponent

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shermanrex.weatherapp.jetpack.weatherapp.R
import com.shermanrex.weatherapp.jetpack.weatherapp.models.ForecastData
import com.shermanrex.weatherapp.jetpack.weatherapp.util.WeatherIconFinder
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailBottomSheet(
    onDismiss: () -> Unit,
    sheetState: SheetState,
    ForecastData: ForecastData,
) {


    ModalBottomSheet(
        onDismissRequest = { onDismiss.invoke() },
        shape = RoundedCornerShape(topEnd = 15.dp, topStart = 15.dp),
        sheetState = sheetState
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BottomSheetLayout(ForecastData)
        }
    }

}

@Composable
private fun BottomSheetLayout(ForecastData: ForecastData) {

    val iconFinder by lazy {
        WeatherIconFinder()
    }

    val verticalState = rememberScrollState()

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(15.dp)
            .verticalScroll(verticalState),
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Text(text = ForecastData.valid_date, fontWeight = FontWeight.Light)
            Image(
                painter = painterResource(
                    id = iconFinder.getIcon(
                        ForecastData.weather.code,
                        ForecastData.weather.icon.contains("n")
                    )
                ),
                contentDescription = "",
                modifier = Modifier
                    .size(90.dp),
                contentScale = ContentScale.Fit,
            )
            Text(text = ForecastData.weather.description, fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = buildAnnotatedString {
                withStyle(
                    SpanStyle(
                        color = MaterialTheme.colorScheme.onSecondary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                    ),
                    block = { append(ForecastData.temp.roundToInt().toString() + "°") }
                )
            }
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row() {
                Text(text = buildAnnotatedString {
                    withStyle(
                        SpanStyle(
                            color = MaterialTheme.colorScheme.onSecondary,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp
                        ),
                        block = { append("H: ") }
                    )
                    withStyle(
                        SpanStyle(
                            color = MaterialTheme.colorScheme.onSecondary,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp
                        ),
                        block = { append(ForecastData.max_temp.roundToInt().toString() + "°") }
                    )
                })
                Text(buildAnnotatedString {
                    withStyle(
                        SpanStyle(
                            color = MaterialTheme.colorScheme.onSecondary,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp
                        ),
                        block = { append("L: ") }
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    withStyle(
                        SpanStyle(
                            color = MaterialTheme.colorScheme.onSecondary,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp
                        ),
                        block = { append(ForecastData.min_temp.roundToInt().toString() + "°") }
                    )
                })
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
        LazyVerticalGrid(columns = GridCells.Fixed(2), Modifier.heightIn(max = 1000.dp), content = {

            val index: String = when (ForecastData.uv.roundToInt()) {
                1, 2 -> "No protection is needed"
                in 3..5 -> "Use protection "
                6, 7 -> "Protection is necessary"
                in 8..10 -> "Extra protection is needed"
                else -> "very harmful"
            }
            val chanceofrain: String = when (ForecastData.precip.roundToInt()) {
                in 0..20 -> "No chance for rain today"
                in 30..50 -> "maybe you should expected rain"
                in 80..100 -> "You should expected rain"
                else -> {
                    ""
                }
            }

            item {
                BottomSheetGridLayout(
                    icon = R.drawable.icon_uv_index,
                    Headline = "Uv Index",
                    Value = ForecastData.uv.roundToInt().toString(),
                    Desc = index
                )
            }
            item {
                BottomSheetGridLayout(
                    icon = R.drawable.icon_humidity,
                    Headline = "Humidity",
                    Value = ForecastData.rh.toString() + " %",
                )
            }
            item {
                BottomSheetGridLayout(
                    icon = R.drawable.icon_pressure,
                    Headline = "Pressure",
                    Value = ForecastData.pres.roundToInt().toString() + " mb",
                )
            }

            item {
                BottomSheetGridLayout(
                    icon = R.drawable.icon_cloud,
                    Headline = "Cloud",
                    Value = ForecastData.clouds.toString(),
                )
            }
            item {
                BottomSheetGridLayout(
                    icon = R.drawable.icon_probabilityofprecipitation,
                    Headline = "Probability of Precipitation",
                    Value = ForecastData.pop.toString(),
                    Desc = chanceofrain
                )
            }
            item {
                BottomSheetGridLayout(
                    icon = R.drawable.precipitation,
                    Headline = "Precipitation",
                    Value = ForecastData.precip.roundToInt().toString() + " mm",
                )
            }

            item {
                BottomSheetGridLayout(
                    icon = R.drawable.icon_visibility,
                    Headline = "Visibility",
                    Value = ForecastData.vis.roundToInt().toString() + " Km",
                )
            }

            item {
                BottomSheetGridLayout(
                    icon = R.drawable.icon_wind_speed,
                    Headline = "Wind Speed",
                    Value = ForecastData.wind_spd.roundToInt().toString() + " m/s",
                )
            }

        })
    }
}


