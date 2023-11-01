package com.uzdev.devicecare.presentation.apps

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.uzdev.core.top.ToolbarScreen
import com.uzdev.devicecare.R
import com.uzdev.devicecare.data.apps.AppManager
import com.uzdev.devicecare.domain.model.App
import com.uzdev.devicecare.navigation.NavRoutes
import com.uzdev.devicecare.theme.FirstTextColor
import com.uzdev.devicecare.theme.MainBackColor
import com.uzdev.devicecare.theme.SecondTextColor
import com.uzdev.devicecare.theme.ThirdColor


@Composable
fun AppsScreen(navHostController: NavHostController) {

    @SuppressLint("StaticFieldLeak")
    val context = LocalContext.current


    val apps: ArrayList<App> = AppManager(context).getInstalledAppList()


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
            items(apps) { app ->
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
    Column(
        Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clickable { onItemClick(app.packageName, navHostController) },

        ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AsyncImage(
                model = app.icon,
                placeholder = painterResource(id = R.drawable.ic_app_place_holder),
                contentScale = ContentScale.Crop,
                contentDescription = null,

                modifier = Modifier
                    .size(60.dp)
                    .align(alignment = Alignment.CenterVertically)
            )
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(start = 8.dp)
            ) {
                val (button, text) = createRefs()
                Text(
                    text = app.name,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .constrainAs(button) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        },
                    color = FirstTextColor,
                    maxLines = 1,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = app.installedDate, textAlign = TextAlign.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(text) {
                            bottom.linkTo(parent.bottom)
                            top.linkTo(button.bottom)
                        }
                        .padding(6.dp), color = SecondTextColor
                )
            }


        }

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
