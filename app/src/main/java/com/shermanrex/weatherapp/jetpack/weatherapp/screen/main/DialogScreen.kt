package com.shermanrex.weatherapp.jetpack.weatherapp.screen.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider

@Composable
fun DialogScreen(
    onclickButton: () -> Unit,
    onclickdismiss: () -> Unit = {},
    icon: Int,
    text: String,
    onclickbuttonText:String,
    showDismissButtun: Boolean = false
) {
    Dialog(
        onDismissRequest = { onclickdismiss.invoke() }, DialogProperties(
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
                    painter = painterResource(id = icon),
                    contentDescription = "",
                    modifier = Modifier.size(100.dp),
                    contentScale = ContentScale.Fit,
                    colorFilter = ColorFilter.tint(Color.White)
                )

                Text(text = text, color = Color.White, modifier = Modifier.padding(15.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = { onclickButton.invoke() }, colors = ButtonDefaults.buttonColors(
                            contentColor = Color.White,
                            containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.3F)
                        )
                    ) {
                        Text(
                            text = onclickbuttonText,
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Justify
                        )
                    }
                    if (showDismissButtun) {
                        Spacer(modifier = Modifier.width(10.dp))
                        Button(
                            onClick = { onclickdismiss.invoke() },
                            colors = ButtonDefaults.buttonColors(
                                contentColor = Color.White,
                                containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(
                                    alpha = 0.4F
                                )
                            )
                        ) {
                            Text(
                                text = "Close",
                                color = Color.White,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }

            }
        }
    }
}
