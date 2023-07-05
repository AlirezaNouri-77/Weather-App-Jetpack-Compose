package com.shermanrex.weatherapp.jetpack.weatherapp.screenComponent

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ChartChips(
    onClick: (WeatherChartEnum) -> Unit,
    modifier: Modifier = Modifier
) {

    val chartList = listOf(
        Pair(WeatherChartEnum.AverageTemp, "Average Temp"),
        Pair(WeatherChartEnum.MaxTemp, "Max Temp"),
        Pair(WeatherChartEnum.MinTemp, "Min Temp"),
        Pair(WeatherChartEnum.Rain, "Rain"),
    )

    val isCheck = remember {
        mutableStateOf(0)
    }

    LazyRow(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        contentPadding = PaddingValues(10.dp)
    ) {

        itemsIndexed(chartList) { index, item ->
            ChartChipsItem(
                chipsText = item.second,
                onClickButton = {
                    isCheck.value = index
                    onClick.invoke(item.first)
                                },
                index = index,
                isCheckIndex = isCheck.value
            )
        }
    }

}

@Composable
fun ChartChipsItem(
    chipsText: String,
    onClickButton: () -> Unit,
    index: Int,
    isCheckIndex: Int
) {

    Button(
        onClick = { onClickButton.invoke() },
        shape = RoundedCornerShape(15.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.6f)),
        modifier = Modifier.padding(5.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isCheckIndex == index) MaterialTheme.colorScheme.onSecondary else Color.Transparent,
        )
    ) {
        Text(
            text = chipsText,
            textAlign = TextAlign.Center,
            fontSize = 12.sp,
            color = if (isCheckIndex == index) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.secondaryContainer,
            fontWeight = FontWeight.SemiBold
        )
    }
}

enum class WeatherChartEnum {
    AverageTemp , MaxTemp , MinTemp , Rain
}