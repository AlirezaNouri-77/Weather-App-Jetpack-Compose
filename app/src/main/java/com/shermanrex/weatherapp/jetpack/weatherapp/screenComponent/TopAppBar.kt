package com.shermanrex.weatherapp.jetpack.weatherapp.screenComponent

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    clickLocationIcon:()-> Unit,
    clickSearchIcon:()-> Unit
) {

    TopAppBar(
        title = { Text(text = "Weather App") },
        actions = {
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "",
                    tint = Color.White,
                    modifier = Modifier.size(25.dp).clickable { clickLocationIcon.invoke() }
                )
            }
            IconButton(onClick = { clickSearchIcon.invoke() }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "",
                    tint = Color.White,
                    modifier = Modifier.size(25.dp)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            titleContentColor = Color.White
        )
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollapseTopBar(
    cityName:String,
    Temp:String
) {
    TopAppBar(
        title = {
            Text(
                text = "$cityName $Temp°",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Left
            )
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = Color.Transparent,
            titleContentColor = Color.White
        )
    )
}