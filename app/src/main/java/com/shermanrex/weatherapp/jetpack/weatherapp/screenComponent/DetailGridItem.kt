package com.shermanrex.weatherapp.jetpack.weatherapp.screenComponent

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shermanrex.weatherapp.jetpack.weatherapp.R
import com.shermanrex.weatherapp.jetpack.weatherapp.ui.theme.WeatherAppTheme

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

@Preview()
@Composable
fun Previewtest() {
    WeatherAppTheme() {
        DetailGridItem(
            icon = R.drawable.precipitation,
            iconDescription = "Precipitation",
            value = 20.toString()
        )
    }
}