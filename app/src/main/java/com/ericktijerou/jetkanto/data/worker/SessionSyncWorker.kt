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
package com.ericktijerou.jetkanto.data.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.ericktijerou.jetkanto.data.local.SessionDataStore
import com.ericktijerou.jetkanto.data.remote.SessionCloudStore
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class SessionSyncWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val sessionDataStore: SessionDataStore,
    private val sessionCloudStore: SessionCloudStore
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        return syncSession()
    }

    private suspend fun syncSession(): Result {
        return try {
            val remoteSession = sessionCloudStore.getUserInfo()
            sessionDataStore.saveSession(remoteSession)
            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure()
        }
    }
}
