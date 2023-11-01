package com.uzdev.devicecare.data.apps

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import com.uzdev.devicecare.data.details.AppInfo
import com.uzdev.devicecare.domain.model.App
import com.uzdev.utils.convertLongToTime

class AppManager(private val context: Context) {

    private val flags = PackageManager.GET_META_DATA or
            PackageManager.GET_SHARED_LIBRARY_FILES

    @SuppressLint("NewApi")
    private val packages =
        context.packageManager.getInstalledPackages(PackageManager.PackageInfoFlags.of(0))

    private val packagesMap: Map<String, PackageInfo> =
        packages.associateBy { packageInfo -> packageInfo.packageName }


    @SuppressLint("QueryPermissionsNeeded", "NewApi")
    fun getInstalledAppList(): ArrayList<App> {
        val apps: ArrayList<App> = ArrayList()

        packages.forEach { packageInfo ->
            apps.add(
                App(
                    packageName = packageInfo.applicationInfo.packageName,
                    name = packageInfo.applicationInfo.loadLabel(context.packageManager).toString(),
                    icon = packageInfo.applicationInfo.loadIcon(context.packageManager),
                    versionName = packageInfo.versionName.toString(),
                    installedDate = packageInfo.firstInstallTime.convertLongToTime()
                )
            )
        }

        val myApps: ArrayList<App> = apps.sortedBy { app: App ->
            app.name.first()
        }.toList() as ArrayList<App>

        return myApps
    }

    fun getAppInfo(packageName: String): AppInfo {
        val packageInfo = packagesMap[packageName]
        return AppInfo(
            name = packageInfo!!.applicationInfo.loadLabel(context.packageManager).toString(),
            icon = packageInfo.applicationInfo.loadIcon(context.packageManager),
            installedTime = packageInfo.firstInstallTime.convertLongToTime(),
            installLocation = packageInfo.applicationInfo.publicSourceDir,
            lastTimeUpdate = packageInfo.lastUpdateTime.convertLongToTime(),
            permission = getAppPermissions(packageName)

        )


    }

    private fun getAppPermissions(packageName: String): ArrayList<String> {

        val packageInfo =
            context.packageManager.getPackageInfo(packageName, PackageManager.GET_PERMISSIONS)
        val permissions: ArrayList<String> = ArrayList()
        packageInfo?.requestedPermissions?.forEach { permission ->
            permissions.add(
                permission
            )
        }
        return permissions
    }
}