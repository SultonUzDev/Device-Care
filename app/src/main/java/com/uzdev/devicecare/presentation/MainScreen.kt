package com.uzdev.devicecare.presentation

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.uzdev.devicecare.R
import com.uzdev.devicecare.data.details.AppDetailsManager
import com.uzdev.devicecare.navigation.NavRoutes
import com.uzdev.devicecare.theme.FirstTextColor
import com.uzdev.devicecare.theme.MainBackColor
import com.uzdev.devicecare.theme.ThirdColor


@Composable
fun MainScreen(navHostController: NavHostController) {
    val context = LocalContext.current

    val appInfo = AppDetailsManager(context = context).getAppUsageByPackageName(
        context.packageName
    )
    Column(
        Modifier
            .fillMaxSize()
            .background(color = MainBackColor),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {

        Image(
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = null,
            modifier = Modifier
                .size(180.dp)
                .align(Alignment.CenterHorizontally)
        )


        Row(
            Modifier
                .fillMaxWidth()
                .padding(6.dp)
                .height(180.dp),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            ImageCardTextView(
                resId = R.drawable.ic_apps, desc = "Apps", modifier = Modifier
                    .size(144.dp)
                    .align(Alignment.CenterVertically)
                    .clickable {
                        navHostController.navigate(NavRoutes.Apps.route)

                    }
            )

            ImageCardTextView(
                resId = R.drawable.ic_storage, desc = "Storage", modifier = Modifier
                    .size(144.dp)
                    .align(Alignment.CenterVertically)
                    .clickable {

                        navHostController.navigate(NavRoutes.Storage.route)
                    }
            )

        }

        Row(
            Modifier
                .fillMaxWidth()
                .padding(6.dp)
                .height(180.dp),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            ImageCardTextView(
                resId = R.drawable.ic_system_info, desc = "System info", modifier = Modifier
                    .size(144.dp)
                    .align(Alignment.CenterVertically)
                    .clickable {
                        Log.d("mlog", "MainScreen: System info")
                    }
            )

            ImageCardTextView(
                resId = R.drawable.ic_check_health, desc = "Health", modifier = Modifier
                    .size(144.dp)
                    .align(Alignment.CenterVertically)
                    .clickable {
                        Log.d("mlog", "MainScreen: Settings")
                    }
            )
        }


    }
}


@Composable
fun ImageCardTextView(resId: Int, desc: String, modifier: Modifier) {
    Card(
        modifier = modifier
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .background(ThirdColor),
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = resId),
                contentDescription = null,
                modifier = Modifier
                    .size(90.dp)
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Text(
                text = desc,
                modifier = Modifier
                    .height(44.dp)
                    .align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold,
                color = FirstTextColor
            )

        }

    }

}

