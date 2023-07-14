package com.shermanrex.weatherapp.jetpack.weatherapp.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.shermanrex.weatherapp.jetpack.weatherapp.screens.WeatherScreen
import com.shermanrex.weatherapp.jetpack.weatherapp.screens.SearchCityScreen
import com.shermanrex.weatherapp.jetpack.weatherapp.viewModel.SearchViewModel
import com.shermanrex.weatherapp.jetpack.weatherapp.viewModel.WeatherViewModel


@Composable
fun MyNavController(navController: NavHostController) {

    val weatherViewModel = viewModel<WeatherViewModel>()
    val searchViewModel = viewModel<SearchViewModel>()

    NavHost(navController = navController, startDestination = NavControllerModel.MainApp.Route) {
        composable(NavControllerModel.MainApp.Route) {
            WeatherScreen(navController, weatherViewModel)
        }
        composable(NavControllerModel.SearchCityScreen.Route) {
            SearchCityScreen(navController, searchViewModel, weatherViewModel)
        }
    }
}

sealed class NavControllerModel(var Route: String) {
    object MainApp : NavControllerModel("MainApp")
    object SearchCityScreen : NavControllerModel("SearchScreenScreen")
}