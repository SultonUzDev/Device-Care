package com.uzdev.devicecare.presentation.splash

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uzdev.devicecare.data.preferences.DataStoreRepository
import com.uzdev.devicecare.navigation.NavRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class SplashViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    private val _startDestination: MutableState<String> = mutableStateOf(NavRoutes.OnBoarding.route)
    val startDestination: State<String> = _startDestination


    private val _isLoading: MutableState<Boolean> = mutableStateOf(true)
    val isLoading: State<Boolean> = _isLoading

    init {
        viewModelScope.launch {
            dataStoreRepository.readOnBoardingState().collect { onBoarded ->
                if (onBoarded) {
                    _startDestination.value = NavRoutes.Main.route
                } else {
                    _startDestination.value = NavRoutes.OnBoarding.route
                }
                _isLoading.value = false
            }

        }
    }


}