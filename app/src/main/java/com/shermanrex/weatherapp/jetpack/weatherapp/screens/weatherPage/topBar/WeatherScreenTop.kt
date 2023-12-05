package com.shermanrex.weatherapp.jetpack.weatherapp.screens.weatherPage.topBar

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherScreenTop(
  onLocationIconClick: () -> Unit,
  onSearchIconClick: () -> Unit
) {
  TopAppBar(
	title = { Text(text = "Weather", fontSize = 28.sp, fontWeight = FontWeight.SemiBold) },
	actions = {
	  IconButton(onClick = { onLocationIconClick.invoke() }) {
		Icon(
		  imageVector = Icons.Default.LocationOn,
		  contentDescription = "",
		  tint = Color.White,
		  modifier = Modifier
			.size(25.dp),
		)
	  }
	  IconButton(onClick = { onSearchIconClick.invoke() }) {
		Icon(
		  imageVector = Icons.Default.Search,
		  contentDescription = "",
		  tint = Color.White,
		  modifier = Modifier.size(25.dp)
		)
	  }
	},
	colors = TopAppBarDefaults.topAppBarColors(
	  containerColor = Color.Transparent,
	  titleContentColor = Color.White
	)
  )
}


