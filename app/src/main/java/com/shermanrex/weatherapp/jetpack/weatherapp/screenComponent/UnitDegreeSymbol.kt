package com.shermanrex.weatherapp.jetpack.weatherapp.screenComponent

import androidx.compose.foundation.clickable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
                color = if (ismetric(currentDegree)) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onPrimary.copy(
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
                color = MaterialTheme.colorScheme.onPrimary
            ),
            block = {
                append(" / ")
            }
        )
        withStyle(
            SpanStyle(
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = if (ismetric(currentDegree)) MaterialTheme.colorScheme.onPrimary.copy(
                    0.5f
                ) else MaterialTheme.colorScheme.onPrimary
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