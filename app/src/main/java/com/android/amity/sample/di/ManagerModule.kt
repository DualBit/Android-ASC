package com.android.amity.sample.di

import com.android.amity.sample.manager.INetworkManager
import com.android.amity.sample.manager.NetworkManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
@Module
@InstallIn(SingletonComponent::class)
abstract class ManagerModule {

    @Binds
    abstract fun bindNetworkManager(
        networkManager: NetworkManager
    ): INetworkManager

}