package com.shermanrex.weatherapp.jetpack.weatherapp.screenComponent

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shermanrex.weatherapp.jetpack.weatherapp.R
import com.shermanrex.weatherapp.jetpack.weatherapp.models.remote.ResponseModel
import com.shermanrex.weatherapp.jetpack.weatherapp.ui.theme.WeatherAppTheme
import kotlinx.coroutines.CoroutineScope

@Composable
fun EventHandler(
  eventState: ResponseModel,
  showSnackBar: Boolean,
  snackBarHostState: SnackbarHostState,
  onSnackBarShowed: () -> Unit = {},
  onRetryClick: () -> Unit = {},
  onDismissClick: () -> Unit = {},
) {
  
  var message = ""
  var snackBarLabel = ""
  var image = 0
  
  when (eventState) {
	ResponseModel.UnAvailableNetwork -> {
	  message = "Please check your internet connection"
	  image = R.drawable.icon_nowifi
	  snackBarLabel = "Try Again"
	}
	
	is ResponseModel.Error -> {
	  message =
		"Something is going wrong please try again later" + "\n" + "Cause: ${eventState.error}"
	  image = R.drawable.icon_warning
	  snackBarLabel = "Try Again"
	}
	
	ResponseModel.UnAvailableGpsService -> {
	  message = "Please turn on Gps service and try again"
	  snackBarLabel = "Try Again"
	}
	
	else -> {}
  }
  
  if (!showSnackBar) {
	EventPage(dialogImage = image, dialogMessage = message) {
	  TextButton(
		onClick = { onRetryClick.invoke() },
		colors = ButtonDefaults.buttonColors(
		  containerColor = Color.Transparent,
		  contentColor = Color.White,
		),
	  ) {
		Text(
		  text = "Try Again",
		  fontSize = 15.sp,
		  fontWeight = FontWeight.SemiBold,
		)
	  }
	}
  } else {
	LaunchedEffect(key1 = eventState, block = {
	  val snackBar = snackBarHostState.showSnackbar(
		message = message,
		withDismissAction = true,
		actionLabel = snackBarLabel,
		duration = SnackbarDuration.Indefinite,
	  )
	  when (snackBar) {
		SnackbarResult.Dismissed -> {
		  onDismissClick.invoke()
		}
		
		SnackbarResult.ActionPerformed -> {
		  onRetryClick.invoke()
		}
	  }
	  onSnackBarShowed.invoke()
	})
  }
  
}

@Composable
private fun EventPage(
  dialogImage: Int,
  dialogMessage: String,
  actions: @Composable () -> Unit = {},
) {
  
  Surface(modifier = Modifier.fillMaxSize(), color = Color.Transparent) {
	Column(
	  horizontalAlignment = Alignment.CenterHorizontally,
	  verticalArrangement = Arrangement.Center,
	  modifier = Modifier
		.wrapContentSize()
		.background(color = Color.Transparent),
	) {
	  if (dialogImage != 0) {
		Image(
		  painter = painterResource(id = dialogImage),
		  contentDescription = "",
		  modifier = Modifier.aspectRatio(5f),
		  contentScale = ContentScale.Fit,
		  colorFilter = ColorFilter.tint(Color.White)
		)
	  }
	  Spacer(modifier = Modifier.height(30.dp))
	  Text(
		text = dialogMessage,
		color = Color.White,
		textAlign = TextAlign.Center,
		fontSize = 16.sp,
		modifier = Modifier.fillMaxWidth(),
	  )
	  Spacer(modifier = Modifier.height(30.dp))
	  Row(
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.Center
	  ) {
		actions.invoke()
	  }
	}
  }
  
}

@Composable
@Preview
fun PreviewDialog() {
  WeatherAppTheme {
	EventPage(
	  dialogImage = R.drawable.icon_nowifi,
	  dialogMessage = "Please check your internet connection",
	)
  }
}