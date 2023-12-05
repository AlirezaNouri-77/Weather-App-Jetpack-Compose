package com.shermanrex.weatherapp.jetpack.weatherapp.screens.searchPage

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.shermanrex.weatherapp.jetpack.weatherapp.models.remote.ResponseModel
import com.shermanrex.weatherapp.jetpack.weatherapp.models.remote.searchModel.SearchCityApiModel
import com.shermanrex.weatherapp.jetpack.weatherapp.navigation.model.NavRoute
import com.shermanrex.weatherapp.jetpack.weatherapp.screens.searchPage.component.SearchItem
import com.shermanrex.weatherapp.jetpack.weatherapp.screens.searchPage.topBar.SearchTopBar
import com.shermanrex.weatherapp.jetpack.weatherapp.util.LottieAnimation
import com.shermanrex.weatherapp.jetpack.weatherapp.viewModel.SearchViewModel

@Composable
fun SearchScreen(
  navController: NavController,
  viewModel: SearchViewModel,
  onClickItem: (Double, Double) -> Unit,
) {
  
  val stateSearchCityApi =
	viewModel.searchApiResultStateflow.collectAsStateWithLifecycle().value
  
  Scaffold(
	containerColor = MaterialTheme.colorScheme.onBackground,
	topBar = {
	  SearchTopBar(
		textFieldChange = {
		  viewModel.callSearchCityApi(it)
		},
		onBackIconClick = { navController.navigate(NavRoute.WeatherScreen.route) },
	  )
	}
  ) {
	
	Column(
	  Modifier
		.padding(it)
		.fillMaxSize(),
	  verticalArrangement = Arrangement.Top,
	  horizontalAlignment = Alignment.CenterHorizontally,
	) {
	  
	  LottieAnimation(showAnimation = stateSearchCityApi == ResponseModel.Loading)
	  
	  when (stateSearchCityApi) {
		
		is ResponseModel.Success<*> -> {
		  
		  val data = (stateSearchCityApi.data) as SearchCityApiModel
		  
		  LazyColumn {
			itemsIndexed(items = data) { _, item ->
			  SearchItem(
				item = item,
				onClick = { lat, lon ->
				  onClickItem.invoke(lat.toDouble(), lon.toDouble())
				},
			  )
			}
		  }
		  
		}
		
		is ResponseModel.Error -> {
		  Text(
			modifier = Modifier
			  .fillMaxWidth()
			  .height(100.dp),
			text = stateSearchCityApi.error,
			fontSize = 18.sp,
			textAlign = TextAlign.Center,
		  )
		}
		
		else -> {}
	  }
	  
	}
  }
}
