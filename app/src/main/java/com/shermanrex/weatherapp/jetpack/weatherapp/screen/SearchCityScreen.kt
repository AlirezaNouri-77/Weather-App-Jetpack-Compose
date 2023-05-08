package com.shermanrex.weatherapp.jetpack.weatherapp.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.navigation.NavController
import com.shermanrex.weatherapp.jetpack.weatherapp.models.SearchCityApiModel
import com.shermanrex.weatherapp.jetpack.weatherapp.models.ResponseResultModel
import com.shermanrex.weatherapp.jetpack.weatherapp.sceenComponent.LottieLoader
import com.shermanrex.weatherapp.jetpack.weatherapp.viewModel.MyviewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged

@OptIn(ExperimentalMaterial3Api::class , FlowPreview::class)
@Composable
fun SearchCityScreen(
    navController: NavController ,
    myviewModel: MyviewModel ,
    Click: (Int) -> Unit
) {

    val stateSearchCityApi = myviewModel.searchCityApiResponse().collectAsState().value

    var textFieldChangeValue by remember {
        mutableStateOf("")
    }

    var isplayingLottie by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = textFieldChangeValue , block = {
        snapshotFlow {
            textFieldChangeValue
        }.debounce(1500L).distinctUntilChanged().collectLatest {
            if (it.length >= 2) {
                myviewModel.callSearchCityApi(it)
            }
        }
    })

    Scaffold(
        topBar = {
            TextField(
                value = textFieldChangeValue ,
                onValueChange = {
                    textFieldChangeValue = it
                } ,
                singleLine = true ,
                leadingIcon = {
                    Row(content = {
                        IconButton(onClick = {
                            navController.popBackStack()
                        }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack ,
                                contentDescription = "" ,
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    })
                } ,
                trailingIcon = {
                    IconButton(onClick = {
                        textFieldChangeValue = ""
                    }) {
                        if (textFieldChangeValue.isNotEmpty()) {
                            Icon(
                                imageVector = Icons.Default.Close ,
                                contentDescription = "" ,
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                } ,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp) ,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = MaterialTheme.colorScheme.primary ,
                    focusedIndicatorColor = Color.Transparent ,
                    unfocusedIndicatorColor = Color.Transparent ,
                    disabledIndicatorColor = Color.Transparent ,
                    cursorColor = MaterialTheme.colorScheme.onPrimary ,
                    textColor = MaterialTheme.colorScheme.onPrimary
                ) ,
                placeholder = {
                    Text(
                        text = "Enter a City Name" ,
                        color = MaterialTheme.colorScheme.onPrimary ,
                        textAlign = TextAlign.Center ,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(15.dp , 0.dp , 15.dp , 0.dp)
                    )
                } ,
                shape = RoundedCornerShape(0.dp) ,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search) ,
                keyboardActions = KeyboardActions(
                    onSearch = {
                        myviewModel.callSearchCityApi(textFieldChangeValue)
                    }
                )
            )
        }
    ) {

        Column(
            Modifier
                .padding(it)
                .fillMaxWidth() ,
            verticalArrangement = Arrangement.Top ,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            AnimatedVisibility(visible = isplayingLottie) {
               LottieLoader()
            }

            when (stateSearchCityApi) {
                is ResponseResultModel.Success -> {
                    isplayingLottie = false
                    LazyColumn {
                        itemsIndexed(stateSearchCityApi.data as SearchCityApiModel) { index , item ->
                            ListItem(
                                headlineText = {
                                    Text(
                                        text = item.name ,
                                        fontWeight = FontWeight.SemiBold ,
                                        fontSize = 16.sp
                                    )
                                } ,
                                supportingText = { Text(text = CodetoFullname(item.country) + "  " + item.state) } ,
                                modifier = Modifier.clickable {
                                    Click(index)
                                })
                        }
                    }
                }

                is ResponseResultModel.Loading -> {
                    isplayingLottie = true
                }

                is ResponseResultModel.Error -> {
                    LazyColumn {
                        item {
                            ListItem(
                                headlineText = {
                                    Text(
                                        text = stateSearchCityApi.error ,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            )
                        }
                    }
                }

                else -> {}

            }

        }
    }
}

private fun CodetoFullname(Code: String): String {
    val locale = java.util.Locale("EN" , Code)
    return locale.getDisplayCountry(locale)
}
