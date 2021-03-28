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

import androidx.work.WorkManager
import com.ericktijerou.jetkanto.domain.repository.RecordRepository
import com.ericktijerou.jetkanto.domain.repository.SessionRepository
import com.ericktijerou.jetkanto.domain.usecase.record.GetRecordUseCase
import com.ericktijerou.jetkanto.domain.usecase.record.SyncRecordUseCase
import com.ericktijerou.jetkanto.domain.usecase.session.GetSessionUseCase
import com.ericktijerou.jetkanto.domain.usecase.session.SyncSessionUseCase
import com.ericktijerou.jetkanto.domain.usecase.shared.GetUiModeUseCase
import com.ericktijerou.jetkanto.domain.usecase.shared.SetDarkModeUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
object DomainModule {

    @Provides
    @ActivityRetainedScoped
    fun provideGetSessionUseCase(sessionRepository: SessionRepository): GetSessionUseCase {
        return GetSessionUseCase(sessionRepository)
    }

    @Provides
    @ActivityRetainedScoped
    fun provideSyncSessionUseCase(workManager: WorkManager): SyncSessionUseCase {
        return SyncSessionUseCase(workManager)
    }

    @Provides
    @ActivityRetainedScoped
    fun provideGetRecordUseCase(recordRepository: RecordRepository): GetRecordUseCase {
        return GetRecordUseCase(recordRepository)
    }

    @Provides
    @ActivityRetainedScoped
    fun provideSyncRecordUseCase(workManager: WorkManager): SyncRecordUseCase {
        return SyncRecordUseCase(workManager)
    }

    @Provides
    @ActivityRetainedScoped
    fun provideGetUiModeUseCase(sessionRepository: SessionRepository): GetUiModeUseCase {
        return GetUiModeUseCase(sessionRepository)
    }

    @Provides
    @ActivityRetainedScoped
    fun provideSetDarkModeUseCase(sessionRepository: SessionRepository): SetDarkModeUseCase {
        return SetDarkModeUseCase(sessionRepository)
    }
}
