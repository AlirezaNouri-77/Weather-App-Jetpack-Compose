package com.shermanrex.weatherapp.jetpack.weatherapp.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.shermanrex.weatherapp.jetpack.weatherapp.screen.main.MainApp
import com.shermanrex.weatherapp.jetpack.weatherapp.screen.main.SearchCityScreen
import com.shermanrex.weatherapp.jetpack.weatherapp.viewModel.MyviewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MyNavController(NavController:NavHostController , myviewModel: MyviewModel){
    NavHost(navController = NavController , startDestination = NavControllerModel.MainApp.Route ){
        composable(NavControllerModel.MainApp.Route){
            MainApp(NavController , myviewModel)
        }

        composable(NavControllerModel.SearchCityScreen.Route){
            SearchCityScreen(NavController,myviewModel)
        }
    }
}

sealed class NavControllerModel(var Route:String){
    object MainApp:NavControllerModel("MainApp")
    object SearchCityScreen:NavControllerModel("SearchScreenScreen")
}