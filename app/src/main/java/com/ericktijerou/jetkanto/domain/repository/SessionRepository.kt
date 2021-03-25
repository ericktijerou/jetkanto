package com.ericktijerou.jetkanto.domain.repository

import com.ericktijerou.jetkanto.domain.entity.User
import kotlinx.coroutines.flow.Flow

interface SessionRepository {
    fun getSession(): Flow<User>
}