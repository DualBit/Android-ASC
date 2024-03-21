package com.android.amity.sample

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import com.amity.socialcloud.sdk.api.core.AmityCoreClient
import com.amity.socialcloud.sdk.api.core.endpoint.AmityEndpoint

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