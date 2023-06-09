package com.shermanrex.weatherapp.jetpack.weatherapp.screenComponent

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.OffsetEffect
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.shermanrex.weatherapp.jetpack.weatherapp.Constant.Constant.IMPERIAL
import com.shermanrex.weatherapp.jetpack.weatherapp.Constant.Constant.METRIC
import com.shermanrex.weatherapp.jetpack.weatherapp.R
import com.shermanrex.weatherapp.jetpack.weatherapp.navigation.NavControllerModel
import com.shermanrex.weatherapp.jetpack.weatherapp.ui.theme.WeatherAppTheme
import java.time.temporal.TemporalQueries.offset

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    navController: NavController
) {

    TopAppBar(
        title = { Text(text = "Weather App") },
        actions = {
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(25.dp)
                )
            }
            IconButton(onClick = { navController.navigate(NavControllerModel.SearchCityScreen.Route) }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(25.dp)
                )
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = Color.Transparent,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollapseTopBar() {
    TopAppBar(
        title = {
            Text(
                text = "Weather App" + "  20 ",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Left
            )
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = Color.Transparent,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}