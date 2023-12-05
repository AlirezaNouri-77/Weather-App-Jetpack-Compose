package com.shermanrex.weatherapp.jetpack.weatherapp.screens.searchPage.component

import androidx.compose.foundation.clickable
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.shermanrex.weatherapp.jetpack.weatherapp.models.remote.searchModel.SearchCityModel
import com.shermanrex.weatherapp.jetpack.weatherapp.util.convertCodeToName


@Composable
fun SearchItem(
  item: SearchCityModel,
  onClick: (lat: String, lon: String) -> Unit,
) {
  ListItem(
	headlineContent = {
	  Text(
		text = item.name,
		fontWeight = FontWeight.SemiBold,
		fontSize = 16.sp
	  )
	},
	supportingContent = {
	  Text(
		text = item.country.convertCodeToName() + "  " + item.state
	  )
	},
	modifier = Modifier.clickable {
	  onClick.invoke(item.lat, item.lon)
	},
	colors = ListItemDefaults.colors(
	  overlineColor = Color.Black,
	  containerColor = Color.Transparent
	)
  )
}