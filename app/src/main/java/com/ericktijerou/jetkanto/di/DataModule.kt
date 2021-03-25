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

import com.ericktijerou.jetkanto.data.local.RecordDataStore
import com.ericktijerou.jetkanto.data.local.SessionDataStore
import com.ericktijerou.jetkanto.data.repository.RecordRepositoryImpl
import com.ericktijerou.jetkanto.data.repository.SessionRepositoryImpl
import com.ericktijerou.jetkanto.domain.repository.RecordRepository
import com.ericktijerou.jetkanto.domain.repository.SessionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
object DataModule {
    @Provides
    @ActivityRetainedScoped
    fun provideSessionRepository(
        dataStore: SessionDataStore,
    ): SessionRepository {
        return SessionRepositoryImpl(dataStore)
    }

    @Provides
    @ActivityRetainedScoped
    fun provideRecordRepository(
        dataStore: RecordDataStore,
    ): RecordRepository {
        return RecordRepositoryImpl(dataStore)
    }
}
