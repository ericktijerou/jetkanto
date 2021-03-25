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

import android.app.Application
import android.content.Context
import com.ericktijerou.jetkanto.data.local.RecordDataStore
import com.ericktijerou.jetkanto.data.local.SessionDataStore
import com.ericktijerou.jetkanto.data.local.dao.RecordDao
import com.ericktijerou.jetkanto.data.local.system.KantoDatabase
import com.ericktijerou.jetkanto.data.local.system.SessionHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {
    @Singleton
    @Provides
    fun provideDatabase(application: Application) = KantoDatabase.getInstance(application)

    @Singleton
    @Provides
    fun provideRecordDao(database: KantoDatabase) = database.getRecordDao()

    @Singleton
    @Provides
    fun provideSessionManager(@ApplicationContext context: Context) = SessionHelper(context)

    @Singleton
    @Provides
    fun provideRecordDataStore(recordDao: RecordDao) = RecordDataStore(recordDao)

    @Singleton
    @Provides
    fun provideSessionDataStore(sessionHelper: SessionHelper) = SessionDataStore(sessionHelper)
}
