package com.android.asc.amity.di

import com.android.asc.amity.manager.INetworkManager
import com.android.asc.amity.manager.NetworkManager
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