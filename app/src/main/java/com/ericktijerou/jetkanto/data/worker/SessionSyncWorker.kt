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
