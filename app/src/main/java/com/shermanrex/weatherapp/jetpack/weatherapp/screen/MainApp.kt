package com.shermanrex.weatherapp.jetpack.weatherapp.screen

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandIn
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.shermanrex.weatherapp.jetpack.weatherapp.R
import com.shermanrex.weatherapp.jetpack.weatherapp.sceenComponent.CollapseTopBar
import com.shermanrex.weatherapp.jetpack.weatherapp.sceenComponent.TopAppBar
import com.shermanrex.weatherapp.jetpack.weatherapp.ui.theme.WeatherAppTheme
import kotlin.math.exp

@OptIn(ExperimentalMaterial3Api::class , ExperimentalAnimationApi::class)
@Composable
fun MainApp(navController: NavController) {

    val listState = rememberLazyListState()

    val iscollapse: Boolean by remember {
        derivedStateOf { listState.firstVisibleItemIndex > 0 }
    }

    Scaffold(
        topBar = {
            AnimatedVisibility(
                visible = iscollapse
            ) {
                CollapseTopBar()
            }
            AnimatedVisibility(visible = !iscollapse) {
                TopAppBar(navController = navController)
            }
        } ,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            Modifier
                .background(
                    brush = Brush.verticalGradient(
                        listOf(
                            Color(0xFF004e92) ,
                            Color(0xFF000428)
                        )
                    )
                )
                .padding(it)
                .fillMaxSize()
        ) {
            LazyColumn(
                Modifier
                    .fillMaxWidth() ,
                verticalArrangement = Arrangement.Center ,
                horizontalAlignment = Alignment.CenterHorizontally ,
                state = listState
            ) {
                item {
                    CurrentWeather()
                }
                item {
                    ThreehourForccast()
                }
                item {
                    SevenDayForecast()
                }
                item {
                    CurrentWeatherDetail()
                }
            }
        }
    }
}

@Composable
private fun CurrentWeather() {
    Box(Modifier.padding(bottom = 15.dp , top = 10.dp)) {
        Column(
            verticalArrangement = Arrangement.Center ,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn ,
                contentDescription = "" ,
                tint = MaterialTheme.colorScheme.onPrimary
            )
            Text(
                text = "Tehran" ,
                fontWeight = FontWeight.Light ,
                fontSize = 24.sp ,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Text(text = buildAnnotatedString {
                withStyle(
                    SpanStyle(
                        fontSize = 48.sp ,
                        fontWeight = FontWeight.SemiBold ,

                        color = MaterialTheme.colorScheme.onPrimary
                    ) ,
                    block = {
                        append("20")
                    }
                )
                withStyle(
                    SpanStyle(
                        fontWeight = FontWeight.Light ,
                        fontSize = 22.sp ,
                        color = MaterialTheme.colorScheme.onPrimary
                    ) ,
                    block = {
                        append(" \u2103")
                    }
                )
            } , Modifier.padding(10.dp))
            Row(
                horizontalArrangement = Arrangement.Center ,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "H: " ,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Text(text = buildAnnotatedString {
                    withStyle(
                        SpanStyle(
                            fontSize = 16.sp ,
                            fontWeight = FontWeight.Light ,
                            color = MaterialTheme.colorScheme.onPrimary
                        ) ,
                        block = {
                            append("20" + "°")
                        }
                    )
                })
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "L: " ,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Text(text = buildAnnotatedString {
                    withStyle(
                        SpanStyle(
                            fontSize = 16.sp ,
                            fontWeight = FontWeight.Light ,
                            color = MaterialTheme.colorScheme.onPrimary
                        ) ,
                        block = {
                            append("20" + "°")
                        }
                    )
                })
            }
        }
    }
}

@Composable
private fun SevenDayForecast() {
    Card(
        Modifier.padding(6.dp) ,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.4F)
        )
    ) {
        Text(
            text = "7-day forecast" ,
            Modifier.padding(top = 10.dp , bottom = 4.dp , start = 10.dp , end = 10.dp) ,
            color = MaterialTheme.colorScheme.onPrimary

        )
        LazyColumn(
            Modifier
                .heightIn(max = 900.dp)
                .padding(top = 10.dp) , content = {
                repeat(7) {
                    item {
                        SevenDayForeCastListItem()
                    }
                }
            })
    }
}

@Composable
private fun SevenDayForeCastListItem() {
    Row(
        horizontalArrangement = Arrangement.Center ,
        verticalAlignment = Alignment.CenterVertically ,
        modifier = Modifier.clickable {

        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally ,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground) ,
                contentDescription = "" ,
                Modifier
                    .size(50.dp)
            )
        }
        Column(
            Modifier
                .weight(0.7F) ,
            horizontalAlignment = Alignment.CenterHorizontally ,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Sunny" , fontSize = 14.sp , fontWeight = FontWeight.Light ,
                color = MaterialTheme.colorScheme.onPrimary ,
            )
            Text(
                text = "2023/12/22" ,
                fontSize = 10.sp ,
                fontWeight = FontWeight.Light , color = MaterialTheme.colorScheme.onPrimary ,
            )
        }

        Column(
            Modifier
                .weight(1F)
                .fillMaxSize() ,
            horizontalAlignment = Alignment.CenterHorizontally ,
            verticalArrangement = Arrangement.Center
        ) {

            Text(text = buildAnnotatedString {
                withStyle(
                    SpanStyle(
                        fontSize = 28.sp ,
                        fontWeight = FontWeight.SemiBold ,
                        color = MaterialTheme.colorScheme.onPrimary ,
                    ) ,
                    block = {
                        append("20" + "°")
                    }
                )
            })
        }

        Column(
            Modifier
                .fillMaxSize()
                .weight(1F) ,
            horizontalAlignment = Alignment.CenterHorizontally ,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                horizontalArrangement = Arrangement.Center ,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    imageVector = Icons.Default.KeyboardArrowUp ,
                    contentDescription = "" , tint = Color(0xFFF44336) ,
                )
                Text(
                    modifier = Modifier.align(alignment = Alignment.CenterVertically) ,
                    text = "Text" ,
                    fontWeight = FontWeight.Light ,
                    color = Color(0xFFF44336) ,
                    fontSize = 12.sp
                )

                Spacer(modifier = Modifier.width(5.dp))

                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown ,
                    contentDescription = "" , tint = Color.Green ,
                )
                Text(
                    modifier = Modifier
                        .align(alignment = Alignment.CenterVertically) ,
                    text = "Text" ,
                    fontWeight = FontWeight.Light ,
                    color = Color.Green ,
                    fontSize = 12.sp
                )

            }
        }
    }
}

@Composable
fun CurrentWeatherDetail() {
    LazyVerticalGrid(
        modifier = Modifier
            .heightIn(max = 900.dp)
            .fillMaxWidth() ,
        columns = GridCells.Fixed(2) ,
        content = {
            repeat(10) {
                item {
                    CurrentWeatherDetailList()
                }
            }
        })
}

@Composable
fun CurrentWeatherDetailList() {
    Card(
        modifier = Modifier.padding(7.dp) ,
        colors = CardDefaults.cardColors(
            MaterialTheme.colorScheme.secondaryContainer.copy(
                alpha = 0.5F
            )
        )
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp) ,
            text = "HEADER" ,
            fontWeight = FontWeight.SemiBold ,
            color = MaterialTheme.colorScheme.onPrimary ,
            fontSize = 18.sp
        )
        Text(
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth()
                .padding(10.dp) ,
            text = "Content" ,
            fontWeight = FontWeight.Medium ,
            color = MaterialTheme.colorScheme.onPrimary ,
            fontSize = 16.sp ,
        )
    }
}


@Composable
fun ThreehourForccast() {
    Card(
        Modifier.padding(6.dp) ,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.4F)
        )
    ) {
        LazyRow(Modifier.fillMaxWidth() , content = {
            repeat(10) {
                item {
                    ThreehourForccastList()
                }
            }
        })
    }

}

@Composable
fun ThreehourForccastList() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally ,
        verticalArrangement = Arrangement.Center ,
        modifier = Modifier.padding(10.dp)
    ) {
        Text(
            text = "05/05" ,
            fontSize = 12.sp ,
            fontWeight = FontWeight.Light ,
            color = MaterialTheme.colorScheme.onPrimary
        )
        Text(
            text = "3" + "°" ,
            fontSize = 12.sp ,
            fontWeight = FontWeight.Light ,
            color = MaterialTheme.colorScheme.onPrimary
        )
        Image(
            painter = painterResource(id = R.drawable.sun) ,
            contentDescription = "" ,
            Modifier.size(25.dp)
        )
        Text(
            text = "20" ,
            fontSize = 15.sp ,
            fontWeight = FontWeight.Light ,
            color = MaterialTheme.colorScheme.onPrimary
        )

    }
}

@Composable
@Preview("Light" , showBackground = true)
@Preview(name = "Dark" , uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
fun preview() {
    WeatherAppTheme {
        MainApp(navController = rememberNavController())
    }
}