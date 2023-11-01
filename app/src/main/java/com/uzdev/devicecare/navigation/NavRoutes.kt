package com.uzdev.devicecare.navigation

sealed class NavRoutes(val route: String) {
    object OnBoarding : NavRoutes("on_boarding")
    object Main : NavRoutes("main")
    object Apps : NavRoutes("apps")
    object AppDetails : NavRoutes("app_details")
    object Storage : NavRoutes("storage")
    object SystemInfo : NavRoutes("system_info")
    object CheckDevice : NavRoutes("check_device")

}