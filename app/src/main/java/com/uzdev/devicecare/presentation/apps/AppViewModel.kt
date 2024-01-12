package com.uzdev.devicecare.presentation.apps

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uzdev.devicecare.data.apps.AppManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(private val appManager: AppManager) : ViewModel() {

    private val _state = mutableStateOf(AppsState())
    val state: State<AppsState> = _state

    init {
        getAppList()
    }

    private fun getAppList() {
        appManager.getAppList().onEach { apps ->
            _state.value = state.value.copy(apps = apps)
        }.launchIn(viewModelScope)

    }

}