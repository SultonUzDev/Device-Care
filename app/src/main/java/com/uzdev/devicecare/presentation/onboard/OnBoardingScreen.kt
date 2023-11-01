package com.uzdev.devicecare.presentation.onboard

import android.app.AppOpsManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Process
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.uzdev.devicecare.navigation.NavRoutes
import com.uzdev.devicecare.theme.FirstTextColor
import com.uzdev.devicecare.theme.MainBackColor
import com.uzdev.devicecare.theme.SecondTextColor
import com.uzdev.devicecare.theme.ThirdColor

@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnBoardingScreen(
    navController: NavController,
    viewModel: OnBoardingViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    var hasAppUsagePerm = false
    val pages =
        listOf(OnBoardingPages.FirstPage, OnBoardingPages.SecondPage, OnBoardingPages.ThirdPage)
    val pagerState = rememberPagerState()
    val observer = LifecycleEventObserver { _, event ->
        if (event == Lifecycle.Event.ON_START) {
            hasAppUsagePerm = getGrantStatus(context)
            viewModel.saveAppUsagePermissionState(hasAppUsagePerm)
        }
    }


    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
    lifecycleOwner.lifecycle.addObserver(observer)

    Column(
        Modifier
            .fillMaxSize()
            .background(MainBackColor),
        verticalArrangement = Arrangement.Top,
    ) {

        HorizontalPager(
            count = 3,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.7f),
            state = pagerState,
            verticalAlignment = Alignment.Top

        ) { position ->
            PagerScreen(page = pages[position])
        }
        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterHorizontally),
            activeColor = Color.White,
            inactiveColor = ThirdColor,

            )
        val modifier = Modifier.weight(1f)

        Box(modifier = modifier) {


            CheckAppUsagePermissionButton(
                modifier = modifier,
                pagerState = pagerState,
                hasPermission = viewModel.stateAppUsagePerm.value
            ) {
                if (!hasAppUsagePerm) {
                    val mIntent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
                    context.startActivity(mIntent)
                } else {
                    Toast.makeText(context, "Already granted", Toast.LENGTH_SHORT).show()
                }
            }

            if (pagerState.currentPage != 0) {
                viewModel.saveAppUsagePermissionState(hasAppUsagePerm)
            }
            FinishButton(modifier = modifier, pagerState = pagerState) {
                viewModel.saveOnBoardingState(true)
                navController.popBackStack()
                navController.navigate(NavRoutes.Main.route)

            }
        }


    }


}




@Composable
fun PagerScreen(page: OnBoardingPages) {
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(page.img))

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        ImageLottieAnim(
            composition,
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .fillMaxHeight(0.7f),
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            text = stringResource(id = page.title),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = FirstTextColor

        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .padding(top = 4.dp),
            text = stringResource(id = page.desc),
            textAlign = TextAlign.Center,
            color = SecondTextColor

        )
    }
}

@Composable
fun ImageLottieAnim(composition: LottieComposition?, modifier: Modifier) {
    LottieAnimation(
        composition = composition,
        iterations = LottieConstants.IterateForever,
        modifier = modifier,
        contentScale = ContentScale.Inside
    )

}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun FinishButton(modifier: Modifier, pagerState: PagerState, onClick: () -> Unit) {

    Row(
        modifier = modifier.padding(horizontal = 40.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Top
    ) {
        AnimatedVisibility(
            modifier = Modifier
                .fillMaxWidth()
                .height(45.dp), visible = pagerState.currentPage == 2
        ) {
            Button(onClick = onClick, shape = RoundedCornerShape(32.dp)) {
                Text(text = "Finish")
            }

        }

    }

}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun CheckAppUsagePermissionButton(
    modifier: Modifier,
    hasPermission: Boolean,
    pagerState: PagerState,
    onClick: () -> Unit

) {

    Row(
        modifier = modifier.padding(horizontal = 40.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Top
    ) {
        AnimatedVisibility(
            modifier = Modifier
                .fillMaxWidth()
                .height(45.dp),
            visible = pagerState.currentPage == 0 && !hasPermission,
        ) {
            Button(onClick = onClick, shape = RoundedCornerShape(32.dp)) {
                Text(text = "Grant")
            }

        }

    }

}

private fun getGrantStatus(context: Context): Boolean {
    val granted: Boolean
    val appOps = context.getSystemService(ComponentActivity.APP_OPS_SERVICE) as AppOpsManager
    val mode = appOps.checkOpNoThrow(
        AppOpsManager.OPSTR_GET_USAGE_STATS,
        Process.myUid(), context.packageName
    )

    granted = if (mode == AppOpsManager.MODE_DEFAULT) {
        context.checkCallingOrSelfPermission(android.Manifest.permission.PACKAGE_USAGE_STATS) == PackageManager.PERMISSION_GRANTED
    } else {
        mode == AppOpsManager.MODE_ALLOWED
    }
    return granted
}
