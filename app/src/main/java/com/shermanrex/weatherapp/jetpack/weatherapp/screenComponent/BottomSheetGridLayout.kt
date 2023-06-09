package com.shermanrex.weatherapp.jetpack.weatherapp.screenComponent

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shermanrex.weatherapp.jetpack.weatherapp.R
import com.shermanrex.weatherapp.jetpack.weatherapp.ui.theme.WeatherAppTheme

@Composable
fun BottomSheetGridLayout(icon: Int, Headline: String, Value: String, Desc: String = "") {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(15.dp)
            .fillMaxSize()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = "",
                modifier = Modifier
                    .size(20.dp)
                    .padding(end = 5.dp)
                    .fillMaxWidth()
                    .weight(0.1F),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSecondary)
            )
            Text(
                text = Headline, fontWeight = FontWeight.Medium, fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.9F)
            )
        }
        Text(
            text = Value,
            Modifier
                .padding(top = 15.dp, start = 10.dp)
                .fillMaxWidth(),
            fontSize = 22.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSecondary
        )
        Text(
            text = Desc,
            fontSize = 13.sp,
            fontWeight = FontWeight.Light,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.fillMaxWidth().padding(start = 10.dp , top = 5.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun Previewtest() {
    WeatherAppTheme() {
        BottomSheetGridLayout(
            icon = R.drawable.precipitation,
            Headline = "Precipitation",
            Value = 20.toString()
        )
    }
}