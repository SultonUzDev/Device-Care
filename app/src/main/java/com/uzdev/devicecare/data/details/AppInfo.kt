package com.uzdev.devicecare.data.details

import android.graphics.drawable.Drawable

data class AppInfo(
    val name: String,
    val icon: Drawable,
    val installedTime: String,
    val installLocation: String,
    val permission: ArrayList<String>,
    val lastTimeUpdate: String
)
