package com.uzdev.devicecare

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.StatFs
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.uzdev.devicecare.navigation.NavRoutes
import com.uzdev.devicecare.navigation.SetNavHost
import com.uzdev.devicecare.presentation.onboard.OnBoardingViewModel
import com.uzdev.devicecare.presentation.splash.SplashViewModel
import com.uzdev.devicecare.theme.DeviceCareTheme
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var viewModel: SplashViewModel


    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().setKeepOnScreenCondition {
            !viewModel.isLoading.value
        }

        setContent {

            DeviceCareTheme {



                val startDestination by viewModel.startDestination
                val navHostController = rememberNavController()
                SetNavHost(
                    navHostController = navHostController, startDestination = startDestination
                )


            }
        }
    }



}

