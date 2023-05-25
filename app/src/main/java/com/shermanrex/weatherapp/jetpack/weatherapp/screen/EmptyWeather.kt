package com.shermanrex.weatherapp.jetpack.weatherapp.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.shermanrex.weatherapp.jetpack.weatherapp.navigation.NavControllerModel

@Composable
fun EmptyWeather(nav: NavController) {
    Column(
        Modifier
            .fillMaxSize()
            .background(color = Color.Transparent) ,
        verticalArrangement = Arrangement.Center ,
        horizontalAlignment = Alignment.CenterHorizontally ,
    ) {
        Text(
            text = "First choose your city for forecast!" ,
            color = MaterialTheme.colorScheme.onPrimary ,
            modifier = Modifier.padding(10.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically ,
            horizontalArrangement = Arrangement.Center
        ) {
            Button(onClick = { } ,
                modifier = Modifier.padding(5.dp) ,
                shape = RoundedCornerShape(15.dp) ,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ) ,
                border = BorderStroke(1.dp , Color.White)) {
                Text(text = "Find by GPS")
            }
            Button(onClick = { nav.navigate(NavControllerModel.SearchCityScreen.Route) } ,
                modifier = Modifier.padding(5.dp) ,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ) ,
                border = BorderStroke(1.dp , Color.White)
            ) {
                Text(text = "Search")
            }

        }
    }

}


@Preview
@Composable
fun previewEmpty() {
    MaterialTheme {
        EmptyWeather(rememberNavController())
    }
}