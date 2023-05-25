package com.shermanrex.weatherapp.jetpack.weatherapp.screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun CurrentWeatherDetail() {
    LazyVerticalGrid(
        modifier = Modifier
            .heightIn(max = 900.dp)
            .fillMaxWidth() ,
        columns = GridCells.Fixed(2) ,
        content = {
            repeat(10) {
                item {
                    CurrentWeatherDetailList()
                }
            }
        })
}

@Composable
fun CurrentWeatherDetailList() {
    Card(
        modifier = Modifier.padding(7.dp) ,
        colors = CardDefaults.cardColors(
            MaterialTheme.colorScheme.secondaryContainer.copy(
                alpha = 0.5F
            )
        )
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp) ,
            text = "HEADER" ,
            fontWeight = FontWeight.SemiBold ,
            color = MaterialTheme.colorScheme.onPrimary ,
            fontSize = 18.sp
        )
        Text(
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth()
                .padding(10.dp) ,
            text = "Content" ,
            fontWeight = FontWeight.Medium ,
            color = MaterialTheme.colorScheme.onPrimary ,
            fontSize = 16.sp ,
        )
    }
}