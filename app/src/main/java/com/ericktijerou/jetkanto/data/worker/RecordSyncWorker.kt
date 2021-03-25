package com.ericktijerou.jetkanto.data.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.ericktijerou.jetkanto.data.local.RecordDataStore
import com.ericktijerou.jetkanto.data.remote.RecordCloudStore
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class RecordSyncWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val recordDataStore: RecordDataStore,
    private val recordCloudStore: RecordCloudStore
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        return syncRecords()
    }

    private suspend fun syncRecords(): Result {
        return try {
            val remoteSession = recordCloudStore.getRecordList()
            recordDataStore.saveRecordList(remoteSession)
            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure()
        }
    }
}
