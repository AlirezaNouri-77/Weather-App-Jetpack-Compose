package com.shermanrex.weatherapp.jetpack.weatherapp.screenComponent

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ChartChips(
    onClick: (WeatherChartEnum) -> Unit,
    modifier: Modifier
) {

    val chartList = listOf(
        Pair(WeatherChartEnum.AverageTemp, "Average Temp"),
        Pair(WeatherChartEnum.MaxTemp, "Max Temp"),
        Pair(WeatherChartEnum.MinTemp, "Min Temp"),
        Pair(WeatherChartEnum.Rain, "Rain"),
        Pair(WeatherChartEnum.UVindex, "Uv index")
    )

    val isCheck = remember {
        mutableStateOf(0)
    }

    LazyHorizontalStaggeredGrid(
        rows = StaggeredGridCells.Fixed(2),
        verticalArrangement = Arrangement.Center,
        horizontalItemSpacing = 5.dp,
        contentPadding = PaddingValues(5.dp),
        modifier = Modifier
            .height(100.dp)
            .fillMaxWidth()
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
    val buttonColor =
        animateColorAsState(
            targetValue = if (isCheckIndex == index) Color(0xFF004e92) else Color.Transparent,
            label = "",
            animationSpec = tween(500),
        ).value
    Button(
        onClick = { onClickButton.invoke() },
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier.padding(5.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.5f)),
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor,
        )
    ) {
        Text(
            text = chipsText,
            textAlign = TextAlign.Center,
            fontSize = 13.sp,
            color = if (isCheckIndex == index) Color.White else MaterialTheme.colorScheme.onPrimary,
            fontWeight = FontWeight.SemiBold
        )
    }
}

enum class WeatherChartEnum {
    AverageTemp, MaxTemp, MinTemp, Rain, UVindex
}