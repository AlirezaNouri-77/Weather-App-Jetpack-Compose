package com.shermanrex.weatherapp.jetpack.weatherapp.sceenComponent

import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp


fun Modifier.shimmerffect(): Modifier = composed {

    var size by remember {
        mutableStateOf(IntSize.Zero)
    }
    val trasition = rememberInfiniteTransition()
    val startoffsetx by trasition.animateFloat(
        initialValue = -2 * size.width.toFloat() ,
        targetValue = 2 * size.width.toFloat() ,
        animationSpec = InfiniteRepeatableSpec(tween(1500))
    )

    background(
        brush = Brush.linearGradient(
            colors = if (isSystemInDarkTheme()) {
                listOf(
                    Color.Black.copy(0.6f) ,
                    Color.White.copy(0.5f) ,
                    Color.Black.copy(0.6f),
                )
            } else {
                listOf(
                    Color.LightGray.copy(0.5f) ,
                    Color.DarkGray.copy(0.5f) ,
                    Color.LightGray.copy(0.5f),
                )
            } ,
            start = Offset(startoffsetx , 0f) ,
            end = Offset(startoffsetx + size.width.toFloat() , size.height.toFloat())
        ),
        shape = RoundedCornerShape(15.dp)
    ).onGloballyPositioned { size = it.size }

}
