package com.ericktijerou.jetkanto.data.repository

import com.ericktijerou.jetkanto.data.entity.toDomain
import com.ericktijerou.jetkanto.data.local.SessionDataStore
import com.ericktijerou.jetkanto.domain.entity.User
import com.ericktijerou.jetkanto.domain.repository.SessionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SessionRepositoryImpl @Inject constructor(
    private val sessionDataStore: SessionDataStore
) : SessionRepository {
    override fun getSession(): Flow<User> {
        return sessionDataStore.getSession().map { it.toDomain() }
    }
}