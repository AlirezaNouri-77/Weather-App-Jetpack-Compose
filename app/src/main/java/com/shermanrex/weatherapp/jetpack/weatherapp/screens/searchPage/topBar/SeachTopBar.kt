package com.shermanrex.weatherapp.jetpack.weatherapp.screens.searchPage.topBar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged

@OptIn(FlowPreview::class)
@Composable
fun SearchTopBar(
  textFieldChange: (String) -> Unit,
  onBackIconClick: () -> Unit,
) {
  
  var textFieldChangeValue by remember {
	mutableStateOf("")
  }
  
  LaunchedEffect(key1 = textFieldChangeValue, block = {
	snapshotFlow {
	  textFieldChangeValue
	}.debounce(1500L).distinctUntilChanged().collectLatest {
	  if (it.isNotEmpty()) {
		textFieldChange.invoke(it.trim().lowercase())
	  }
	}
  })
  
  TextField(
	value = textFieldChangeValue,
	onValueChange = {
	  textFieldChangeValue = it
	},
	singleLine = true,
	leadingIcon = {
	  Row(content = {
		IconButton(
		  onClick = {
			onBackIconClick.invoke()
		  },
		) {
		  Icon(
			imageVector = Icons.Default.ArrowBack,
			contentDescription = "",
			tint = Color.White,
		  )
		}
	  })
	},
	trailingIcon = {
	  AnimatedVisibility(
		visible = textFieldChangeValue.isNotEmpty(),
		enter = scaleIn(), exit = scaleOut(),
	  ) {
		IconButton(onClick = {
		  textFieldChangeValue = ""
		}) {
		  Icon(
			imageVector = Icons.Default.Close,
			contentDescription = "",
			tint = Color.White,
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
	  focusedPlaceholderColor = Color.White.copy(alpha = 0.6F),
	  unfocusedPlaceholderColor = Color.White.copy(alpha = 0.6F),
	  unfocusedIndicatorColor = Color.Transparent,
	  focusedTextColor = Color.White,
	  cursorColor = Color.White,
	),
	placeholder = {
	  Text(
		text = "Enter a City Name",
		textAlign = TextAlign.Center,
		fontSize = 18.sp,
		modifier = Modifier
		  .fillMaxWidth()
	  )
	},
	shape = RoundedCornerShape(0.dp),
	keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
  )
  
}
