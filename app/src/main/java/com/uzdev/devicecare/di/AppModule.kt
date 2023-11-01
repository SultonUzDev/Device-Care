package com.uzdev.devicecare.di

import android.content.Context
import com.uzdev.devicecare.data.preferences.DataStoreRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object AppModule {


    @Provides
    fun provideDataStoreRepository(@ApplicationContext ctx: Context) = DataStoreRepository(ctx)
}