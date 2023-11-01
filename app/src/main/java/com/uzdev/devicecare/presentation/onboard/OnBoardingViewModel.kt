package com.uzdev.devicecare.presentation.onboard

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uzdev.devicecare.data.preferences.DataStoreRepository
import com.uzdev.devicecare.navigation.NavRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(private val repository: DataStoreRepository) :
    ViewModel() {

    private val _stateAppUsagePerm: MutableState<Boolean> = mutableStateOf(true)
    val stateAppUsagePerm: State<Boolean> = _stateAppUsagePerm



    init {
        viewModelScope.launch {
            repository.readAppUsagePermissionState().collect { hasPerm ->
                _stateAppUsagePerm.value = hasPerm
            }

        }
    }

    fun saveOnBoardingState(state: Boolean) {
        viewModelScope.launch {
            repository.saveOnBoardingState(state)
        }
    }

    fun saveAppUsagePermissionState(granted: Boolean) {
        viewModelScope.launch {
            repository.saveAppUsagePermissionState(granted)
        }
    }





}