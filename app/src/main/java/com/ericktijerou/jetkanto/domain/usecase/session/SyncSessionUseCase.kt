package com.ericktijerou.jetkanto.domain.usecase.session

import androidx.work.*
import com.ericktijerou.jetkanto.data.worker.SessionSyncWorker
import java.util.*
import javax.inject.Inject

class SyncSessionUseCase @Inject constructor(private val workManager: WorkManager) {
    operator fun invoke(): UUID {
        val sessionSyncWorker = OneTimeWorkRequestBuilder<SessionSyncWorker>()
            .setConstraints(getRequiredConstraints())
            .build()

        workManager.enqueueUniqueWork(
            SYNC_TASK_NAME,
            ExistingWorkPolicy.REPLACE,
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