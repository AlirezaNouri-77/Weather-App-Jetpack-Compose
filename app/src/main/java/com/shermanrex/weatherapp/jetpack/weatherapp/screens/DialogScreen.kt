package com.shermanrex.weatherapp.jetpack.weatherapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider

@Composable
fun DialogScreen(
    dialogImage: Int,
    dialogMessage: String,
    dialogDismiss: () -> Unit = {},
    buttons: @Composable () -> Unit

) {
    Dialog(
        onDismissRequest = { dialogDismiss.invoke() }, DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
        )
    ) {
        (LocalView.current.parent as DialogWindowProvider).window.setDimAmount(0.8f)
        Surface(modifier = Modifier.fillMaxSize() , color = Color.Transparent) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .wrapContentSize()
                    .background(color = Color.Transparent),
            ) {
                Image(
                    painter = painterResource(id = dialogImage),
                    contentDescription = "",
                    modifier = Modifier.size(100.dp),
                    contentScale = ContentScale.Fit,
                    colorFilter = ColorFilter.tint(Color.White)
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(text = dialogMessage, color = Color.White, modifier = Modifier.padding(15.dp))
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    buttons.invoke()
                }
            }
        }
    }
}
