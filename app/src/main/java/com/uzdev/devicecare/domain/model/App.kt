package com.uzdev.devicecare.domain.model

import android.graphics.drawable.Drawable

data class App(
    val packageName: String,
    val name: String,
    val icon: Drawable,
    val versionName: String,
    val installedDate: String,

    )
