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
package com.ericktijerou.jetkanto.domain.usecase.session

import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.ericktijerou.jetkanto.data.worker.SessionSyncWorker
import java.util.UUID
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SyncSessionUseCase @Inject constructor(private val workManager: WorkManager) {
    operator fun invoke(): UUID {
        val sessionSyncWorker = PeriodicWorkRequestBuilder<SessionSyncWorker>(1, TimeUnit.DAYS)
            .setConstraints(getRequiredConstraints())
            .build()

        workManager.enqueueUniquePeriodicWork(
            SYNC_TASK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            sessionSyncWorker
        )

        return sessionSyncWorker.id
    }

    private fun getRequiredConstraints(): Constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

    companion object {
        const val SYNC_TASK_NAME = "task_session"
    }
}
