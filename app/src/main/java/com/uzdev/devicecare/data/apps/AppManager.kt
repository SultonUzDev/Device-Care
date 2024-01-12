package com.uzdev.devicecare.data.apps

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.LauncherApps
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.UserManager
import com.uzdev.devicecare.data.details.AppInfo
import com.uzdev.devicecare.domain.model.App
import com.uzdev.devicecare.utils.convertLongToTime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AppManager(private val context: Context) {


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

    fun getAppList(): Flow<List<App>> {
        val appList: ArrayList<App> = ArrayList()
        try {
            val userManager = context.getSystemService(Context.USER_SERVICE) as UserManager
            val launcherApps =
                context.getSystemService(Context.LAUNCHER_APPS_SERVICE) as LauncherApps

            for (profile in userManager.userProfiles) {
                for (app in launcherApps.getActivityList(null, profile)) {


                    val packageName = app.applicationInfo.packageName
                    val appName =
                        app.label.toString().ifBlank { packageName }
                    val icon = app.applicationInfo.loadIcon(context.packageManager)

                    appList.add(App(packageName = packageName, name = appName, icon = icon))

                }
            }
            appList.sortedBy { it.name.lowercase() }

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return flow {
            emit(appList)
        }

    }


}