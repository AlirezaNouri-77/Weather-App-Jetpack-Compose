package com.shermanrex.weatherapp.jetpack.weatherapp.screens.weatherPage

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.shermanrex.weatherapp.jetpack.weatherapp.models.remote.ResponseModel
import com.shermanrex.weatherapp.jetpack.weatherapp.models.remote.WeatherResponseData
import com.shermanrex.weatherapp.jetpack.weatherapp.navigation.model.NavRoute
import com.shermanrex.weatherapp.jetpack.weatherapp.screenComponent.EventHandler
import com.shermanrex.weatherapp.jetpack.weatherapp.screenComponent.ShimmerLoading
import com.shermanrex.weatherapp.jetpack.weatherapp.screens.weatherPage.component.SevenDayForecastSection
import com.shermanrex.weatherapp.jetpack.weatherapp.screens.weatherPage.component.ThreeHourForecastSection
import com.shermanrex.weatherapp.jetpack.weatherapp.screens.weatherPage.component.TodayWeatherDetailSection
import com.shermanrex.weatherapp.jetpack.weatherapp.screens.weatherPage.component.TodayWeatherSection
import com.shermanrex.weatherapp.jetpack.weatherapp.screens.weatherPage.topBar.WeatherScreenTop
import com.shermanrex.weatherapp.jetpack.weatherapp.util.LottieAnimation
import com.shermanrex.weatherapp.jetpack.weatherapp.viewModel.WeatherViewModel
import kotlinx.coroutines.launch

@Composable
fun WeatherScreen(
  navController: NavController,
  viewModel: WeatherViewModel,
) {
  
  val snackBarHostState = SnackbarHostState()
  val listState = rememberLazyListState()
  val scope = rememberCoroutineScope()
  val context = LocalContext.current
  
  val responseState = viewModel.weatherResponse.collectAsStateWithLifecycle().value
  
  val locationPermission = rememberLauncherForActivityResult(
	contract = ActivityResultContracts.RequestPermission()
  ) {
	when (it) {
	  true -> viewModel.getWeather()
	  false -> {
		scope.launch {
		  val snack = snackBarHostState.showSnackbar(
			message = "Gps permission is not granted",
			withDismissAction = true,
			actionLabel = "Go to Setting",
			duration = SnackbarDuration.Indefinite,
		  )
		  when (snack) {
			SnackbarResult.Dismissed -> {}
			SnackbarResult.ActionPerformed -> {
			  val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
			  intent.data = Uri.fromParts("package", context.packageName, null)
			  context.startActivities(arrayOf(intent))
			}
		  }
		}
	  }
	}
  }
  
  Scaffold(
	topBar = {
	  WeatherScreenTop(
		onLocationIconClick = {
		  val checkPermission =
			ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
		  if (checkPermission == PackageManager.PERMISSION_DENIED) {
			scope.launch {
			  locationPermission.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
			}
		  } else if (checkPermission == PackageManager.PERMISSION_GRANTED) {
			viewModel.getWeather()
		  }
		},
		onSearchIconClick = { navController.navigate(NavRoute.SearchCityScreen.route) },
	  )
	},
	snackbarHost = {
	  SnackbarHost(snackBarHostState) {
		Snackbar(
		  snackbarData = it,
		  containerColor = MaterialTheme.colorScheme.errorContainer,
		  contentColor = Color.Black,
		  actionColor = MaterialTheme.colorScheme.error,
		  dismissActionContentColor = MaterialTheme.colorScheme.error,
		)
	  }
	},
	modifier = Modifier.fillMaxSize(),
  ) {
	
	Box(
	  Modifier
		.background(
		  brush = Brush.verticalGradient(
			listOf(
			  Color(0xFF00325E),
			  Color(0xFF000428)
			)
		  )
		)
		.padding(it)
		.fillMaxSize(),
	) {
	  
	  LazyColumn(
		Modifier
		  .fillMaxWidth(),
		verticalArrangement = Arrangement.Center,
		horizontalAlignment = Alignment.CenterHorizontally,
		state = listState,
	  ) {
		
		item {
		  LottieAnimation(showAnimation = responseState == ResponseModel.Loading && viewModel.weatherData.value.currentWeatherData != null)
		  viewModel.weatherData.value.currentWeatherData?.let { it1 ->
			TodayWeatherSection(
			  data = it1,
			  weatherUnitDataStore = viewModel.getWeatherUnitDataStore(),
			  onClickDegree = { unit ->
				viewModel.run {
				  updateUnitDataStore(unit)
				  getWeatherByCoordinator()
				}
			  },
			)
		  }
		}
		
		item {
		  viewModel.weatherData.value.threeHourWeatherData?.let { it1 ->
			ThreeHourForecastSection(
			  data = it1
			)
		  }
		  viewModel.weatherData.value.sevenDayWeatherData?.let { it1 ->
			SevenDayForecastSection(data = it1)
		  }
		  viewModel.weatherData.value.currentWeatherData?.let { it1 ->
			TodayWeatherDetailSection(
			  data = it1
			)
		  }
		}
		
	  }
	  
	  if (responseState is ResponseModel.Error ||
		responseState == ResponseModel.UnAvailableGpsService ||
		responseState == ResponseModel.UnAvailableNetwork
	  ) {
		EventHandler(
		  eventState = responseState,
		  snackBarHostState = snackBarHostState,
		  showSnackBar = viewModel.weatherData.value.currentWeatherData != null,
		  onRetryClick = { viewModel.getWeather() },
		  onSnackBarShowed = { viewModel.messageShowed() },
		)
	  }
	  
	  when (responseState) {
		ResponseModel.Loading -> {
		  if (viewModel.weatherData.value.currentWeatherData == null) ShimmerLoading()
		}
		
		is ResponseModel.Success<*> -> {
		  viewModel.weatherData.value = ((responseState.data as WeatherResponseData))
		}
		
		else -> {}
	  }
	  
	}
  }
}


