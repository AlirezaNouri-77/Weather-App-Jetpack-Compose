package com.shermanrex.weatherapp.jetpack.weatherapp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.shermanrex.weatherapp.jetpack.weatherapp.util.shimmerffect

@Composable
fun Shimmerloading() {
    Column(
        verticalArrangement = Arrangement.Top ,
        horizontalAlignment = Alignment.CenterHorizontally ,
        modifier = Modifier.fillMaxSize()
    ) {
        Box {
            Column(
                verticalArrangement = Arrangement.Center ,
                horizontalAlignment = Alignment.CenterHorizontally ,
            ) {
                Spacer(modifier = Modifier.size(10.dp))
                Box(
                    modifier = Modifier
                        .width(100.dp)
                        .height(30.dp)
                        .shimmerffect()
                )
                Spacer(modifier = Modifier.size(10.dp))
                Box(
                    modifier = Modifier
                        .width(120.dp)
                        .height(40.dp)
                        .shimmerffect()
                )
                Spacer(modifier = Modifier.size(10.dp))
                Box(
                    modifier = Modifier
                        .width(80.dp)
                        .height(30.dp)
                        .shimmerffect()
                )
                Spacer(modifier = Modifier.size(10.dp))
                Box(
                    modifier = Modifier
                        .width(100.dp)
                        .height(30.dp)
                        .shimmerffect()
                )
                Spacer(modifier = Modifier.size(10.dp))
                Box(
                    modifier = Modifier
                        .width(80.dp)
                        .height(20.dp)
                        .shimmerffect()
                )
                Spacer(modifier = Modifier.size(10.dp))
                Surface(
                    modifier = Modifier
                        .padding(start = 10.dp , end = 10.dp)
                        .shimmerffect()
                        .fillMaxWidth()
                        .height(70.dp) , shape = RoundedCornerShape(10.dp) ,
                    color = Color.Transparent
                ) {}
                Spacer(modifier = Modifier.size(10.dp))
                Surface(
                    modifier = Modifier
                        .padding(start = 10.dp , end = 10.dp)
                        .shimmerffect()
                        .fillMaxWidth()
                        .height(500.dp) ,
                    color = Color.Transparent
                ) {}

            }
        }
    }
}