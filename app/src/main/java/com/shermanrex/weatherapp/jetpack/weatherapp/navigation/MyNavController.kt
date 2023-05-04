package com.shermanrex.weatherapp.jetpack.weatherapp.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.shermanrex.weatherapp.jetpack.weatherapp.screen.MainApp
import com.shermanrex.weatherapp.jetpack.weatherapp.screen.SearchCityScreen
import com.shermanrex.weatherapp.jetpack.weatherapp.viewModel.SearchCityApiViewModel

@Composable
fun MyNavController(NavController:NavHostController , searchCityApiViewModel: SearchCityApiViewModel){
    NavHost(navController = NavController , startDestination = NavControllerModel.MainApp.Route ){
        composable(NavControllerModel.MainApp.Route){
            MainApp(NavController)
        }
        composable(NavControllerModel.SearchCityScreen.Route){
            SearchCityScreen(NavController,searchCityApiViewModel , Click = {
                Log.d("TAG" , "MyNavController: " + it)
            })
        }
    }
}

sealed class NavControllerModel(var Route:String){
    object MainApp:NavControllerModel("MainApp")
    object SearchCityScreen:NavControllerModel("SearchScreenScreen")
}