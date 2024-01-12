package com.uzdev.devicecare.presentation.apps

import com.uzdev.devicecare.domain.model.App

data class AppsState(val apps: List<App> = emptyList())