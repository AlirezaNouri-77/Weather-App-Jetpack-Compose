package com.shermanrex.weatherapp.jetpack.weatherapp.util

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.shermanrex.weatherapp.jetpack.weatherapp.R

@Composable
fun LottieLoader() {
    val composition =
        rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.lottieloadingsearch))
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        verticalArrangement = Arrangement.Top ,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieAnimation(
            composition = composition.value ,
            contentScale = ContentScale.Fit
        )
    }
}
