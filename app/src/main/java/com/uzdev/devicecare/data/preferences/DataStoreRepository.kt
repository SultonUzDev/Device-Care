package com.uzdev.devicecare.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.preferencesOf
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "on_boarding")

class DataStoreRepository(context: Context) {
    private object DataStorePreferenceKey {

        val onBoarded = booleanPreferencesKey(name = "onboard")
        val isAppUsageGranted = booleanPreferencesKey(name = "granted")

    }

    private val dataStore = context.dataStore


    suspend fun saveOnBoardingState(onBoarded: Boolean) {
        dataStore.edit {
            it[DataStorePreferenceKey.onBoarded] = onBoarded
        }

    }


    suspend fun saveAppUsagePermissionState(granted: Boolean) {
        dataStore.edit {
            it[DataStorePreferenceKey.isAppUsageGranted] = granted
        }
    }

    suspend fun readOnBoardingState(): Flow<Boolean> {
        return dataStore.data.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }

        }.map {
            val onBoarded = it[DataStorePreferenceKey.onBoarded] ?: false
            onBoarded
        }

    }


    suspend fun readAppUsagePermissionState(): Flow<Boolean> {
        return dataStore.data.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else throw exception
        }.map {
            val granted = it[DataStorePreferenceKey.isAppUsageGranted] ?: false
            granted
        }
    }

}