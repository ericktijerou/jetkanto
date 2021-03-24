package com.ericktijerou.jetkanto.data.local

import com.ericktijerou.jetkanto.data.entity.UserModel
import com.ericktijerou.jetkanto.data.local.system.SessionManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SessionDataStore @Inject constructor(
    private val sessionManager: SessionManager,
) {
    fun getSession(): Flow<UserModel> = sessionManager.session

    suspend fun saveSession(newSession: UserModel) {
        sessionManager.saveSession(newSession)
    }
}