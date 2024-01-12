package com.uzdev.devicecare.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun ToolbarScreen(navHostController: NavHostController, title: String) {


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {

        Icon(
            Icons.Default.ArrowBack,

            contentDescription = null,

            modifier = Modifier
                .width(50.dp)
                .height(35.dp)
                .align(Alignment.CenterStart)
                .clickable {
                    navHostController.navigateUp()
                },
            tint = Color.White,


            )
        Text(
            text = title,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.Center),
            color = Color.Yellow,
            fontWeight = FontWeight.ExtraBold
        )
    }


}