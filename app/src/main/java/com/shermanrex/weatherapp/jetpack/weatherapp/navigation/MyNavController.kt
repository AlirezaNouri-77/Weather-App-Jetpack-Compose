package com.shermanrex.weatherapp.jetpack.weatherapp.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
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

    NavHost(
        navController = navController, startDestination = NavControllerModel.MainApp.route,
    ) {
        composable(NavControllerModel.MainApp.route,
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = (tween(500))
                )
            },
            exitTransition = {
                slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = (tween(500))
                    )
            }, popEnterTransition = {
                slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = (tween(500)),)
            }) {
            BackHandler(enabled = true , onBack = {})
            WeatherScreen(navController, weatherViewModel)
        }
        composable(NavControllerModel.SearchCityScreen.route,
            enterTransition = {
                slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = (tween(500)),)
            },
            exitTransition = {
                slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = (tween(500)),)
            }) {
            SearchCityScreen(navController, searchViewModel, weatherViewModel)
        }
    }
}

sealed class NavControllerModel(var route: String) {
    object MainApp : NavControllerModel("MainApp")
    object SearchCityScreen : NavControllerModel("SearchScreenScreen")
}