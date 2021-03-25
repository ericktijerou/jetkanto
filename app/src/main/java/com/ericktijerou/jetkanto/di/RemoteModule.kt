/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ericktijerou.jetkanto.di

import android.content.Context
import com.ericktijerou.jetkanto.core.NetworkConnectivity
import com.ericktijerou.jetkanto.data.remote.api.KantoApi
import com.ericktijerou.jetkanto.data.remote.util.buildOkHttpClient
import com.ericktijerou.jetkanto.data.remote.util.buildRetrofit
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return buildOkHttpClient()
    }

    @Provides
    @Singleton
    fun provideApollo(okHttpClient: OkHttpClient): KantoApi {
        return buildRetrofit(okHttpClient).create(KantoApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNetworkConnectivity(@ApplicationContext context: Context): NetworkConnectivity {
        return NetworkConnectivity(context)
    }
}
