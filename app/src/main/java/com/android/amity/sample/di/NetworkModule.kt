package com.android.amity.sample.di

import com.android.amity.sample.BuildConfig
import com.android.amity.sample.datastore.ISharedPrefDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
    ): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(BuildConfig.BASE_URL)
            .build()
    }

    @Provides
    fun provideOkHttpClient(dataStore: ISharedPrefDataStore): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor {
                val requestBuilder = it.request().newBuilder()
                val user = runBlocking { dataStore.userDataStore.firstOrNull() }
                user?.token?.let { token ->
                    requestBuilder.header("Authorization", token)
                }
                it.proceed(requestBuilder.build())
            }
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .callTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .build()
    }
}