package com.shermanrex.weatherapp.jetpack.weatherapp.screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.shermanrex.weatherapp.jetpack.weatherapp.navigation.NavControllerModel
import com.shermanrex.weatherapp.jetpack.weatherapp.ui.theme.WeatherAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(navController: NavController) {
    TopAppBar(
        title = { Text(text = "Weather App") } ,
        actions = {
            IconButton(onClick = { navController.navigate(NavControllerModel.SearchCityScreen.Route) }) {
                Icon(
                    imageVector = Icons.Default.Search ,
                    contentDescription = "" ,
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        } ,
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = Color.Transparent ,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollapseTopBar() {
    TopAppBar(
        title = {
            Text(
                text = "Weather App" + "  20 " ,
                modifier = Modifier.fillMaxWidth() ,
                textAlign = TextAlign.Left
            )
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = Color.Transparent ,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    WeatherAppTheme {
        TopAppBar(rememberNavController())
    }
}