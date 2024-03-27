package com.android.asc.amity.di

import android.content.Context

import androidx.datastore.preferences.preferencesDataStore
import com.android.asc.amity.datastore.ISharedPrefDataStore
import com.android.asc.amity.datastore.SharedPrefDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    private val Context.dataStore by preferencesDataStore(SharedPrefDataStore.USER_PREFS)

    @Provides
    fun provideDataStore(@ApplicationContext context: Context): ISharedPrefDataStore =
        SharedPrefDataStore(context.dataStore)
}