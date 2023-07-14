package com.shermanrex.weatherapp.jetpack.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import com.shermanrex.weatherapp.jetpack.weatherapp.navigation.MyNavController
import com.shermanrex.weatherapp.jetpack.weatherapp.ui.theme.WeatherAppTheme
import com.shermanrex.weatherapp.jetpack.weatherapp.viewModel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppTheme {
                val weatherViewModel = viewModels<WeatherViewModel>()
                weatherViewModel.value.callWeatherRepository()
                val navController = rememberNavController()
                MyNavController(navController = navController)
            }
        }
    }
}