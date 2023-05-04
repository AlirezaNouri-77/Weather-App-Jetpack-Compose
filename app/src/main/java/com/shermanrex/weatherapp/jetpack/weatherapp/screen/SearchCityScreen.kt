package com.shermanrex.weatherapp.jetpack.weatherapp.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
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
import com.shermanrex.weatherapp.jetpack.weatherapp.models.ResponseResult
import com.shermanrex.weatherapp.jetpack.weatherapp.viewModel.SearchCityApiViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce

@OptIn(ExperimentalMaterial3Api::class , FlowPreview::class)
@Composable
fun SearchCityScreen(
    navController: NavController ,
    searchCityApiViewModel: SearchCityApiViewModel ,
    Click: (Int) -> Unit
) {

    val stateSearchCityApi = searchCityApiViewModel.searchCityApiResponse().collectAsState().value

    var textFieldChangeValue by remember {
        mutableStateOf("")
    }

    LaunchedEffect(key1 = textFieldChangeValue , block = {
        snapshotFlow {
            textFieldChangeValue
        }.debounce(1500L).collectLatest {
            if (it.length >= 2) {
                searchCityApiViewModel.callSearchCityApi(it)
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
                shape = RoundedCornerShape(0.dp),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        searchCityApiViewModel.callSearchCityApi(textFieldChangeValue)
                    }
                )
            )
        }
    ) {

        Column(
            Modifier
                .padding(it)
                .fillMaxWidth() ,
            verticalArrangement = Arrangement.Center ,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (stateSearchCityApi) {
                is ResponseResult.SearchSuccess -> {
                    LazyColumn {
                        itemsIndexed(stateSearchCityApi.data) { index , item ->
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

                is ResponseResult.Loading -> {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Magenta)
                            .align(Alignment.CenterHorizontally) ,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CircularProgressIndicator(
                            strokeWidth = 5.dp
                        )
                        Spacer(modifier = Modifier.width(20.dp))
                        Text(text = "Please Wait")
                    }
                }

                is ResponseResult.Error -> {
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
