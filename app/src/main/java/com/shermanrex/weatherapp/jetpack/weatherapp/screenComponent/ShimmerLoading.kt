package com.shermanrex.weatherapp.jetpack.weatherapp.screenComponent

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shermanrex.weatherapp.jetpack.weatherapp.ui.theme.WeatherAppTheme
import com.shermanrex.weatherapp.jetpack.weatherapp.util.shimmerEffect

@Composable
fun ShimmerLoading() {
  Column(
	verticalArrangement = Arrangement.Top,
	horizontalAlignment = Alignment.CenterHorizontally,
	modifier = Modifier.fillMaxSize()
  ) {
	Box {
	  Column(
		verticalArrangement = Arrangement.Center,
		horizontalAlignment = Alignment.CenterHorizontally,
	  ) {
		Spacer(modifier = Modifier.size(10.dp))
		Spacer(
		  modifier = Modifier
			.width(60.dp)
			.height(20.dp)
			.clip(shape = RoundedCornerShape(12.dp))
			.shimmerEffect()
		)
		Spacer(modifier = Modifier.size(10.dp))
		Spacer(
		  modifier = Modifier
			.width(120.dp)
			.height(60.dp)
			.clip(shape = RoundedCornerShape(12.dp))
			.shimmerEffect()
		)
		Spacer(modifier = Modifier.size(10.dp))
		Spacer(
		  modifier = Modifier
			.width(80.dp)
			.height(30.dp)
			.clip(shape = RoundedCornerShape(12.dp))
			.shimmerEffect()
		)
		Spacer(modifier = Modifier.size(10.dp))
		Spacer(
		  modifier = Modifier
			.width(100.dp)
			.height(30.dp)
			.clip(shape = RoundedCornerShape(12.dp))
			.shimmerEffect()
		)
		Spacer(modifier = Modifier.size(15.dp))
		Spacer(
		  modifier = Modifier
			.fillMaxWidth(fraction = 0.95f)
			.height(120.dp)
			.clip(shape = RoundedCornerShape(12.dp))
			.shimmerEffect(),
		)
		Spacer(modifier = Modifier.size(15.dp))
		Spacer(
		  modifier = Modifier
			.fillMaxWidth(fraction = 0.95f)
			.height(500.dp)
			.clip(shape = RoundedCornerShape(12.dp))
			.shimmerEffect(),
		)
		
	  }
	}
  }
}

@Composable
@Preview
fun PreviewShimmerLoading() {
  WeatherAppTheme {
	ShimmerLoading()
  }
}

