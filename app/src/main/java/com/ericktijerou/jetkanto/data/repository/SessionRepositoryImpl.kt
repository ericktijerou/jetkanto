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
package com.ericktijerou.jetkanto.data.repository

import com.ericktijerou.jetkanto.data.entity.toDomain
import com.ericktijerou.jetkanto.data.entity.toLocal
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

    override suspend fun updateSession(user: User) {
        return sessionDataStore.saveSession(user.toLocal())
    }
}
