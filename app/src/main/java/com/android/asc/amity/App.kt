package com.android.asc.amity

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import com.amity.socialcloud.sdk.api.core.AmityCoreClient
import com.amity.socialcloud.sdk.api.core.endpoint.AmityEndpoint
import com.android.asc.amity.BuildConfig

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initAmity()
    }
    private fun initAmity() {
        AmityCoreClient.setup(
            apiKey = BuildConfig.amityApiKey,
            endpoint = AmityEndpoint.EU,
        )
    }

}