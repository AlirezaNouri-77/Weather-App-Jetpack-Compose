package com.shermanrex.weatherapp.jetpack.weatherapp.screenComponent

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.shermanrex.weatherapp.jetpack.weatherapp.Constant.Constant
import com.shermanrex.weatherapp.jetpack.weatherapp.screen.main.ismetric

@Composable
fun UnitDegreeSymbol(currentDegree: String, onclick: () -> Unit) {
    Text(text = buildAnnotatedString {
        withStyle(
            SpanStyle(
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = if (ismetric(currentDegree)) Color.White else Color.White.copy(
                    0.5f
                )
            ),
            block = {
                append(Constant.CELSIUS_SYMBOL)
            }
        )
        withStyle(
            SpanStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            ),
            block = {
                append(" / ")
            }
        )
        withStyle(
            SpanStyle(
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = if (ismetric(currentDegree)) Color.White.copy(
                    0.5f
                ) else Color.White
            ),
            block = {
                append(Constant.FAHRENHEIT_SYMBOL)
            }
        )
    }, Modifier
        .clickable {
            onclick()
        })
}