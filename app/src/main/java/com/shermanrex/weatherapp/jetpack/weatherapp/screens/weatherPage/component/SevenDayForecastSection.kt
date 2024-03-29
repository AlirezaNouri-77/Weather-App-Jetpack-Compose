package com.shermanrex.weatherapp.jetpack.weatherapp.screens.weatherPage.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shermanrex.weatherapp.jetpack.weatherapp.R
import com.shermanrex.weatherapp.jetpack.weatherapp.screenComponent.bottomsheet.ChartBottomSheet
import com.shermanrex.weatherapp.jetpack.weatherapp.screenComponent.bottomsheet.DetailBottomSheet
import com.shermanrex.weatherapp.jetpack.weatherapp.models.remote.weatherModels.SevenDayForecastModel
import com.shermanrex.weatherapp.jetpack.weatherapp.util.WeatherIconFinder
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SevenDayForecastSection(data: SevenDayForecastModel) {

    val weatherIconFinder by lazy {
        WeatherIconFinder()
    }

    var showBottomSheetChart by remember {
        mutableStateOf(false)
    }

    val bottomState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    if (showBottomSheetChart) {
        ChartBottomSheet(
            onDismiss = { showBottomSheetChart = false },
            sheetState = bottomState,
            ForecastData = data.data
        )
    }

    Card(
        Modifier.padding(top = 5.dp, start = 10.dp, end = 10.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onBackground
            //  containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.3F)
        )
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "7-day forecast",
                Modifier
                    .padding(top = 10.dp, bottom = 4.dp, start = 10.dp, end = 10.dp)
                    .weight(1f),
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
            )
            Image(
                painter = painterResource(id = R.drawable.icon_barchart),
                contentDescription = "",
                modifier = Modifier
                    .weight(1f)
                    .wrapContentSize(align = Alignment.CenterEnd)
                    .padding(top = 10.dp, bottom = 4.dp, start = 10.dp, end = 20.dp)
                    .clickable {
                        showBottomSheetChart = true
                    },
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary)
            )
        }

        LazyColumn(
            Modifier
                .heightIn(max = 900.dp)
                .padding(top = 10.dp),
        ) {
            itemsIndexed(data.data) { index, item ->
                if (index != 0) {
                    SevenDayForeCastListItem(
                        ForecastData = item,
                        WeatherIconFinder = weatherIconFinder,
                    )
                }
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SevenDayForeCastListItem(
  ForecastData: SevenDayForecastModel.Data,
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
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.clickable {
            showBottomSheet = true
        }
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                Modifier
                    .weight(1F),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Image(
                    painter = painterResource(
                        id = WeatherIconFinder.getIconResource(
                            ForecastData.weather.code,
                            ForecastData.weather.icon.contains("n")
                        )
                    ),
                    contentDescription = "",
                    Modifier
                        .size(40.dp)
                        .padding(start = 5.dp)
                )

                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(start = 5.dp)
                ) {
                    Text(
                        text = ForecastData.weather.description,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Light,
                        color = MaterialTheme.colorScheme.onPrimary,
                    )
                    Text(
                        text = ForecastData.validDate,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Light,
                        color = MaterialTheme.colorScheme.onPrimary,
                    )
                }

            }
            Column(
                Modifier
                    .weight(0.5F),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(text = buildAnnotatedString {
                    withStyle(
                        SpanStyle(
                            fontSize = 24.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onPrimary,
                        ),
                        block = {
                            append(ForecastData.temp.roundToInt().toString() + "°")
                        }
                    )
                })
            }
            Column(
                Modifier
                    .fillMaxWidth()
                    .weight(1F),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(start = 5.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.rainchance),
                        modifier = Modifier
                            .size(15.dp)
                            .padding(end = 5.dp),
                        contentDescription = "",
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary),
                    )
                    Text(
                        text = "${ForecastData.pop}%",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowUp,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                    Text(
                        modifier = Modifier.align(alignment = Alignment.CenterVertically),
                        text = ForecastData.maxTemp.roundToInt().toString(),
                        fontWeight = FontWeight.Light,
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                    Text(
                        modifier = Modifier
                            .align(alignment = Alignment.CenterVertically),
                        text = ForecastData.minTemp.roundToInt().toString(),
                        fontWeight = FontWeight.Light,
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 12.sp
                    )
                }
            }
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = if (!showDetail) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                contentDescription = "",
                modifier = Modifier.size(15.dp),
                tint = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5F)
            )
        }
        if (showBottomSheet) {
            DetailBottomSheet(
                onDismiss = { showBottomSheet = false },
                ForecastData = ForecastData,
                sheetState = bottomState,
            )
        }

    }
}

