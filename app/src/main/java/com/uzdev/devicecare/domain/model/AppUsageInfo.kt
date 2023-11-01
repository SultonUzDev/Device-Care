package com.uzdev.devicecare.domain.model

data class AppUsageInfo(
    val packageName: String,
    val totalUsagePercent: Int? ,
    val totalUsageTime: String?,
    val lastTimeUsed: String?

)
