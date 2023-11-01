package com.uzdev.devicecare.presentation.onboard

import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import androidx.annotation.StringRes
import com.uzdev.devicecare.R

sealed class OnBoardingPages(
    @RawRes val img: Int,
    @StringRes val title: Int,
    @StringRes val desc: Int
) {

    object FirstPage : OnBoardingPages(
        img = R.raw.anim_apps,
        title = R.string.apps,
        desc = R.string.apps_desc
    )

    object SecondPage : OnBoardingPages(
        img = R.raw.anim_cpu,
        title = R.string.storage_cpu,
        desc = R.string.storage_and_cpu
    )

    object ThirdPage : OnBoardingPages(
        img = R.raw.anim_device_health,
        title = R.string.device_health,
        desc = R.string.device_health_desc
    )
}


