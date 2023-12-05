package com.shermanrex.weatherapp.jetpack.weatherapp.util

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOut
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.shermanrex.weatherapp.jetpack.weatherapp.R
import com.shermanrex.weatherapp.jetpack.weatherapp.ui.theme.WeatherAppTheme
import kotlinx.coroutines.CoroutineScope

@Composable
fun LottieAnimation(
  showAnimation: Boolean,
) {
  
  val lottie = if (isSystemInDarkTheme()) R.raw.whitelottiejson else R.raw.blacklottiejson
  
  val composition =
	rememberLottieComposition(spec = LottieCompositionSpec.RawRes(lottie))
  
  AnimatedVisibility(
	visible = showAnimation,
	enter = slideIn { IntOffset(0, -it.height) },
	exit = slideOut { IntOffset(0, -it.height) },
  ) {
	Box(modifier = Modifier.fillMaxWidth().size(60.dp)) {
	  LottieAnimation(
		composition = composition.value,
		modifier = Modifier,
		contentScale = ContentScale.Inside,
		iterations = LottieConstants.IterateForever,
	  )
	}
  }
  
}