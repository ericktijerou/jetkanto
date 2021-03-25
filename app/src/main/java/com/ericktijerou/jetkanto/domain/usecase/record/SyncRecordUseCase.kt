package com.ericktijerou.jetkanto.domain.usecase.record

import androidx.work.*
import com.ericktijerou.jetkanto.data.worker.RecordSyncWorker
import java.util.*
import javax.inject.Inject

class SyncRecordUseCase @Inject constructor(private val workManager: WorkManager) {
    operator fun invoke(): UUID {
        val recordSyncWorker = OneTimeWorkRequestBuilder<RecordSyncWorker>()
            .setConstraints(getRequiredConstraints())
            .build()

        workManager.enqueueUniqueWork(
            SYNC_TASK_NAME,
            ExistingWorkPolicy.REPLACE,
            recordSyncWorker
        )

        return recordSyncWorker.id
    }

    private fun getRequiredConstraints(): Constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

    companion object {
        const val SYNC_TASK_NAME = "task_session"
    }
}