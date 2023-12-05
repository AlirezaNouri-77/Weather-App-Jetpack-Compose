package com.shermanrex.weatherapp.jetpack.weatherapp

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.shermanrex.weatherapp.jetpack.weatherapp.navigation.MyNavController
import com.shermanrex.weatherapp.jetpack.weatherapp.ui.theme.WeatherAppTheme
import com.shermanrex.weatherapp.jetpack.weatherapp.viewModel.SearchViewModel
import com.shermanrex.weatherapp.jetpack.weatherapp.viewModel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
	super.onCreate(savedInstanceState)
	setContent {
	  WeatherAppTheme {
		MyNavController()
	  }
	}
  }
}