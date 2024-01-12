package com.uzdev.devicecare.data.details

import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.pm.PackageManager
import com.uzdev.devicecare.domain.model.AppUsageInfo
import com.uzdev.devicecare.utils.convertLongToTime
import com.uzdev.devicecare.utils.getDurationBreakdown

class AppDetailsManager(private val context: Context) {
    private val usageStatsManager =
        context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager

    private val beginTime = System.currentTimeMillis() - 1000 * 3600 * 24
    private val endTime = System.currentTimeMillis()
    private val usageStats: Map<String, UsageStats> =
        usageStatsManager.queryAndAggregateUsageStats(beginTime, endTime)

    private val totalUsageAllApps =
        usageStats.values.stream().map { obj -> obj.totalTimeInForeground }
            .mapToLong { obj: Long -> obj }.sum()

    private val flags =
        PackageManager.GET_META_DATA




    fun getAppUsageByPackageName(packageName: String): AppUsageInfo? {
        if (isAppInfoAvailable(packageName = packageName)) {
            val appUsage = usageStats[packageName]
            val totalTime = appUsage?.totalTimeInForeground
            val lastTimeUsed = appUsage?.lastTimeUsed
            val percentage = ((totalTime?.times(100))?.div(totalUsageAllApps))?.toInt()


            return AppUsageInfo(
                packageName = packageName,
                totalUsageTime = totalTime?.getDurationBreakdown(),
                lastTimeUsed = lastTimeUsed?.convertLongToTime(),
                totalUsagePercent = percentage
            )

        } else {
            return null
        }
    }

    private fun isAppInfoAvailable(packageName: String): Boolean {
        return try {
            context.applicationContext.packageManager.getApplicationInfo(
                packageName,
                flags
            )
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }


}