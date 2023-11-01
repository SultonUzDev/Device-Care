package com.uzdev.devicecare.presentation.apps.details

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.uzdev.devicecare.R
import com.uzdev.devicecare.data.apps.AppManager
import com.uzdev.devicecare.data.details.AppDetailsManager
import com.uzdev.devicecare.theme.FirstTextColor
import com.uzdev.devicecare.theme.MainBackColor
import com.uzdev.devicecare.theme.SecondTextColor
import com.uzdev.devicecare.theme.ThirdColor

@Composable
fun AppDetailsScreen(navHostController: NavHostController, packageName: String) {
    val mCtx = LocalContext.current
    val appUsage = AppDetailsManager(mCtx).getAppUsageByPackageName(packageName)
    val appInfo = AppManager(mCtx).getAppInfo(packageName = packageName)
    Log.d("myLog", "AppDetails: ")
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MainBackColor)
            .padding(
                start = 3.dp,
                end = 3.dp,
                top = 1.dp,
                bottom = 1.dp
            )
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top
    ) {


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


        }

        AsyncImage(
            model = appInfo.icon,
            placeholder = painterResource(id = R.drawable.ic_app_place_holder),
            contentDescription = null,
            modifier = Modifier
                .size(100.dp)
                .padding(top = 8.dp, bottom = 8.dp)
                .align(Alignment.CenterHorizontally)

        )

        Line()
        TitleTextView(title = "What is the app name?")
        DescriptionTextView(desc = "The app name is ${appInfo.name}")
        Line()
        TitleTextView(title = "Where is the app installed?")
        DescriptionTextView(desc = "The app installed on ${appInfo.installLocation}")
        Line()
        TitleTextView(title = "When was the app installed?")
        DescriptionTextView(desc = "The app installed on ${appInfo.installedTime}")

        Line()
        TitleTextView(title = "When was the app last updated?")
        DescriptionTextView(desc = "The app updated on ${appInfo.lastTimeUpdate}")
        Line()
        TitleTextView(title = "How much have you used it? (%)")
        DescriptionTextView(desc = "You have used this app about ${appUsage?.totalUsagePercent} %")

        Line()
        TitleTextView(title = "How much have you used it? (H)")
        DescriptionTextView(desc = "You have used this app about ${appUsage?.totalUsageTime}")

        Line()
        TitleTextView(title = "When was the last time you used this app?")
        DescriptionTextView(desc = "You last used this app on ${appUsage?.lastTimeUsed}")

        Line()
        TitleTextView(title = "What permission does the app have?")
        if (appInfo.permission.isNotEmpty()) {
            appInfo.permission.forEach { permission ->
                DescriptionTextView(desc = permission)
            }
        }

    }

}


@Composable
fun TitleTextView(title: String) {
    Text(
        text = title,
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .padding(8.dp),
        textAlign = TextAlign.Left,
        fontWeight = FontWeight.SemiBold,
        color = FirstTextColor,
        letterSpacing = 0.5.sp,

        )

}

@Composable
fun DescriptionTextView(desc: String) {
    Text(
        text = desc,
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .padding(start = 8.dp, end = 4.dp, top = 2.dp, bottom = 2.dp),
        textAlign = TextAlign.Left,
        color = SecondTextColor,
        letterSpacing = 0.5.sp,

        )
}

@Composable
fun Line() {
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .padding(start = 6.dp)
            .background(
                brush = Brush.linearGradient(
                    listOf(
                        Color.White,
                        Color.Gray,
                        ThirdColor
                    )
                )
            )
    )
}