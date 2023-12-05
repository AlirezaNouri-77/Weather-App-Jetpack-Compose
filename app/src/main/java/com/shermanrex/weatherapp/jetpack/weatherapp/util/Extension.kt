package com.shermanrex.weatherapp.jetpack.weatherapp.util

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize
import java.util.Locale

fun String.convertCodeToName(): String {
  return Locale("EN", this).run {
	this.getDisplayCountry(this)
  }
}

fun Modifier.shimmerEffect(): Modifier = composed {
  
  var size by remember {
	mutableStateOf(IntSize.Zero)
  }
  
  val transition = rememberInfiniteTransition(label = "")
  
  val transitionFloat by transition.animateFloat(
	initialValue = 0f,
	targetValue = size.width.toFloat() + size.width.toFloat() / 2,
	animationSpec = infiniteRepeatable(
	  tween(durationMillis = 2000),
	  repeatMode = RepeatMode.Restart,
	),
	label = "shimmerEffect",
  )
  
  background(
	Brush.horizontalGradient(
	  colors = if (isSystemInDarkTheme()) {
		listOf(
		  Color.Black.copy(0.6f),
		  Color.White.copy(0.5f),
		  Color.Black.copy(0.6f),
		)
	  } else {
		listOf(
		  Color.LightGray.copy(0.6f),
		  Color.DarkGray.copy(0.5f),
		  Color.LightGray.copy(0.6f),
		)
	  },
	  startX = transitionFloat - size.width / 4,
	  endX = transitionFloat * 3f,
	),
  ).onGloballyPositioned { size = it.size }
  
}