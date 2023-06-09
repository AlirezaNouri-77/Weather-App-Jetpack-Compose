package com.shermanrex.weatherapp.jetpack.weatherapp.screenComponent

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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import kotlin.math.roundToInt

@Composable
fun SevenDayForecast(data: SevenDayForecastModel) {

    val WeatherIconFinder by lazy {
        WeatherIconFinder()
    }

    Card(
        Modifier.padding(top = 5.dp  , start = 10.dp, end = 10.dp) ,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.3F)
        )
    ) {
        Text(
            text = "7-day forecast" ,
            Modifier.padding(top = 10.dp , bottom = 4.dp , start = 10.dp , end = 10.dp) ,
            color = MaterialTheme.colorScheme.onPrimary,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp
        )

        LazyColumn(
            Modifier
                .heightIn(max = 900.dp)
                .padding(top = 10.dp) ,
        ) {
            itemsIndexed(data.data) { index , item ->
                if (index!=0) {
                    SevenDayForeCastListItem(ForecastData = item , WeatherIconFinder = WeatherIconFinder)
                }
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SevenDayForeCastListItem(
    ForecastData: ForecastData,
    WeatherIconFinder: WeatherIconFinder
) {

    var showBottomSheet by remember {
        mutableStateOf(false)
    }

    val bottomState = rememberModalBottomSheetState()

    val showDetail by rememberSaveable {
        mutableStateOf(false)
    }

    Column(
        verticalArrangement = Arrangement.Center ,
        modifier = Modifier.clickable {
            showBottomSheet = true
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
                            ForecastData.weather.code ,
                            ForecastData.weather.icon.contains("n")
                        )
                    ) ,
                    contentDescription = "" ,
                    Modifier
                        .size(40.dp)
                        .padding(start = 5.dp)
                )

                Column(
                    horizontalAlignment = Alignment.Start ,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(start = 5.dp)
                ) {
                    Text(
                        text = ForecastData.weather.description ,
                        fontSize = 14.sp ,
                        fontWeight = FontWeight.Light ,
                        color = MaterialTheme.colorScheme.onPrimary ,
                    )
                    Text(
                        text = ForecastData.valid_date ,
                        fontSize = 12.sp ,
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
                            append(ForecastData.temp.roundToInt().toString() + "°")
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
                        text = ForecastData.max_temp.roundToInt().toString() ,
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
                        text = ForecastData.min_temp.roundToInt().toString() ,
                        fontWeight = FontWeight.Light ,
                        color = MaterialTheme.colorScheme.onPrimary ,
                        fontSize = 12.sp
                    )
                }
            }
        }

        if(showBottomSheet) {
            DetailBottomSheet(onDismiss = { showBottomSheet = false } , ForecastData = ForecastData, sheetState = bottomState)
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
