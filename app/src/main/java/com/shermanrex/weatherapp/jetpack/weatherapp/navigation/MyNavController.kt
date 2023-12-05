package com.shermanrex.weatherapp.jetpack.weatherapp.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shermanrex.weatherapp.jetpack.weatherapp.navigation.model.NavRoute
import com.shermanrex.weatherapp.jetpack.weatherapp.screens.weatherPage.WeatherScreen
import com.shermanrex.weatherapp.jetpack.weatherapp.screens.searchPage.SearchScreen
import com.shermanrex.weatherapp.jetpack.weatherapp.viewModel.SearchViewModel
import com.shermanrex.weatherapp.jetpack.weatherapp.viewModel.WeatherViewModel

@Composable
fun MyNavController(
  weatherViewModel: WeatherViewModel = hiltViewModel(),
  searchViewModel: SearchViewModel = hiltViewModel(),
) {

  val navController = rememberNavController()
  
  val route = if (!weatherViewModel.checkDataStoreIsEmpty()) {
	NavRoute.SearchCityScreen.route
  } else {
	NavRoute.WeatherScreen.route
  }
  
  NavHost(
	navController = navController,
	startDestination = route,
  ) {
	
	composable(
	  NavRoute.WeatherScreen.route,
	  enterTransition = {
		slideIntoContainer(
		  towards = AnimatedContentTransitionScope.SlideDirection.Down,
		  animationSpec = tween(200)
		) + fadeIn(tween(200))
	  },
	  exitTransition = {
		slideOutOfContainer(
		  towards = AnimatedContentTransitionScope.SlideDirection.Up,
		  animationSpec = tween(200)
		) + fadeOut(tween(200))
	  },
	  popEnterTransition = {
		slideIntoContainer(
		  towards = AnimatedContentTransitionScope.SlideDirection.Down,
		  animationSpec = tween(200)
		) + fadeIn(tween(200))
	  },
	  popExitTransition = {
		slideOutOfContainer(
		  towards = AnimatedContentTransitionScope.SlideDirection.Up,
		  animationSpec = tween(200)
		) + fadeOut(tween(200))
	  },
	) {
	  WeatherScreen(
		navController = navController,
		viewModel = weatherViewModel,
	  )
	}
	
	composable(
	  NavRoute.SearchCityScreen.route,
	  enterTransition = {
		slideIntoContainer(
		  towards = AnimatedContentTransitionScope.SlideDirection.Up,
		  animationSpec = tween(200)
		) + fadeIn(tween(200))
	  },
	  exitTransition = {
		slideOutOfContainer(
		  towards = AnimatedContentTransitionScope.SlideDirection.Down,
		  animationSpec = tween(200)
		) + fadeOut(tween(200))
	  },
	  popExitTransition = {
		slideOutOfContainer(
		  towards = AnimatedContentTransitionScope.SlideDirection.Down,
		  animationSpec = tween(200)
		) + fadeOut(tween(200))
	  },
	  popEnterTransition = {
		slideIntoContainer(
		  towards = AnimatedContentTransitionScope.SlideDirection.Up,
		  animationSpec = tween(200)
		) + fadeIn(tween(200))
	  },
	) {
	  SearchScreen(
		navController = navController,
		viewModel = searchViewModel,
		onClickItem = { lat, lon ->
		  weatherViewModel.updateCoordinatorDataStore(
			latitude = lat,
			longitude = lon,
		  )
		  weatherViewModel.getWeatherByCoordinator()
		  navController.navigate(NavRoute.WeatherScreen.route)
		},
	  )
	}
	
  }
}