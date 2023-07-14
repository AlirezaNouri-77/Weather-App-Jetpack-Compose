package com.shermanrex.weatherapp.jetpack.weatherapp.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.shermanrex.weatherapp.jetpack.weatherapp.models.ResponseResultModel
import com.shermanrex.weatherapp.jetpack.weatherapp.models.SearchCityApiModel
import com.shermanrex.weatherapp.jetpack.weatherapp.navigation.NavControllerModel
import com.shermanrex.weatherapp.jetpack.weatherapp.util.LottieLoader
import com.shermanrex.weatherapp.jetpack.weatherapp.viewModel.SearchViewModel
import com.shermanrex.weatherapp.jetpack.weatherapp.viewModel.WeatherViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.runBlocking

@OptIn(FlowPreview::class)
@Composable
fun SearchCityScreen(
    navController: NavController,
    searchViewModel: SearchViewModel,
    weatherViewModel: WeatherViewModel
) {

    val stateSearchCityApi =
        searchViewModel.searchCityApiResponse().collectAsStateWithLifecycle().value

    var textFieldChangeValue by remember {
        mutableStateOf("")
    }

    var isplayingLottie by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = textFieldChangeValue, block = {
        snapshotFlow {
            textFieldChangeValue
        }.debounce(1500L).distinctUntilChanged().collectLatest {
            if (it.length >= 2) {
                searchViewModel.callSearchCityApi(it.trim().lowercase())
            }
        }
    })

    Scaffold(
        topBar = {
            TextField(
                value = textFieldChangeValue,
                onValueChange = {
                    textFieldChangeValue = it
                },
                singleLine = true,
                leadingIcon = {
                    Row(content = {
                        IconButton(onClick = {
                            navController.navigate(NavControllerModel.MainApp.Route)
                        }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = ""
                            )
                        }
                    })
                },
                trailingIcon = {
                    IconButton(onClick = {
                        textFieldChangeValue = ""
                    }) {
                        if (textFieldChangeValue.isNotEmpty()) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = ""
                            )
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFF004e92),
                    unfocusedContainerColor = Color(0xFF004e92),
                    cursorColor = Color.White,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedPlaceholderColor = Color.White.copy(alpha = 0.6F),
                    focusedTrailingIconColor = Color.White,
                    focusedLeadingIconColor = Color.White,
                    unfocusedPlaceholderColor = Color.White.copy(alpha = 0.6F),
                    unfocusedTrailingIconColor = Color.White,
                    unfocusedLeadingIconColor = Color.White
                ),
                placeholder = {
                    Text(
                        text = "Enter a City Name",
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(15.dp, 0.dp, 15.dp, 0.dp)
                    )
                },
                shape = RoundedCornerShape(0.dp),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        searchViewModel.callSearchCityApi(textFieldChangeValue)
                    }
                )
            )
        }
    ) {

        Column(
            Modifier
                .padding(it)
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.onBackground),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            AnimatedVisibility(visible = isplayingLottie) {
                LottieLoader()
            }

            when (stateSearchCityApi) {
                is ResponseResultModel.SearchSuccess -> {
                    LazyColumn {
                        itemsIndexed(stateSearchCityApi.data) { index, item ->
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
                                        text = codetoFullname(item.country) + "  " + if (item.state != null) item.state else {
                                            ""
                                        }
                                    )
                                },
                                modifier = Modifier.clickable {
                                    runBlocking {
                                        weatherViewModel.updateCoordinatorDataStore(
                                            stateSearchCityApi.data[index].lat.toDouble(),
                                            stateSearchCityApi.data[index].lon.toDouble()
                                        )
                                    }
                                    weatherViewModel.callWeatherRepositorySearchScreen(
                                        stateSearchCityApi.data[index].lat.toDouble(),
                                        stateSearchCityApi.data[index].lon.toDouble(),
                                    )
                                    navController.navigate(NavControllerModel.MainApp.Route)
                                },
                                colors = ListItemDefaults.colors(
                                    overlineColor = Color.Black,
                                    containerColor = Color.Transparent
                                )
                            )
                        }
                    }
                    isplayingLottie = false
                }

                is ResponseResultModel.Loading -> {
                    isplayingLottie = true
                }

                is ResponseResultModel.Error -> {
                    isplayingLottie = false
                    LazyColumn {
                        item {
                            ListItem(
                                headlineContent = {
                                    Text(
                                        text = stateSearchCityApi.error,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }, colors = ListItemDefaults.colors(
                                    overlineColor = Color.White,
                                    containerColor = Color.Transparent
                                )
                            )
                        }
                    }
                }

                else -> {}
            }

        }
    }
}

private fun codetoFullname(Code: String): String {
    val locale = java.util.Locale("EN", Code)
    return locale.getDisplayCountry(locale)
}
