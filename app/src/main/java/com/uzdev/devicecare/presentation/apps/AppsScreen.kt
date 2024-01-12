package com.uzdev.devicecare.presentation.apps

import android.annotation.SuppressLint
import android.util.Log
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.uzdev.devicecare.presentation.components.ToolbarScreen
import com.uzdev.devicecare.R
import com.uzdev.devicecare.domain.model.App
import com.uzdev.devicecare.navigation.NavRoutes
import com.uzdev.devicecare.theme.FirstTextColor
import com.uzdev.devicecare.theme.MainBackColor
import com.uzdev.devicecare.theme.ThirdColor


@Composable
fun AppsScreen(navHostController: NavHostController, appViewModel: AppViewModel = hiltViewModel()) {

    @SuppressLint("StaticFieldLeak")
    val context = LocalContext.current


    var apps: List<App> = emptyList()


    Column(
        Modifier
            .fillMaxSize()
            .background(MainBackColor)
    ) {
        LazyColumn(
            modifier = Modifier.padding(
                start = 3.dp,
                end = 3.dp,
                top = 1.dp,
                bottom = 1.dp
            )
        ) {
            item {
                ToolbarScreen(navHostController, title = "All your application")
            }
            appViewModel.state.value.apps.let {
                apps = it
            }


            items(apps) { app ->

                Log.d("mlog", "ppp: ${app.name}");
                ItemScreen(app, navHostController)
                Divider(
                    Modifier
                        .height(0.8.dp)
                        .fillMaxWidth(), color = ThirdColor
                )
            }


        }


    }

}

@Composable
fun ItemScreen(app: App, navHostController: NavHostController) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clickable { onItemClick(app.packageName, navHostController) },
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = app.icon,
            placeholder = painterResource(id = R.drawable.ic_app_place_holder),
            contentScale = ContentScale.Crop,
            contentDescription = null,

            modifier = Modifier
                .size(60.dp)
                .padding(8.dp)
                .align(alignment = Alignment.CenterVertically)
        )
        Text(
            text = app.name,
            textAlign = TextAlign.Start,
            modifier = Modifier.weight(1f).padding(8.dp),
            color = FirstTextColor,
            maxLines = 1,
            fontWeight = FontWeight.SemiBold
        )
    }


}

fun onItemClick(packageName: String, navHostController: NavHostController) {
    navHostController.navigate(
        NavRoutes.AppDetails.route + "/{packageName}".replace(
            oldValue = "{packageName}",
            newValue = packageName
        )
    )
}
