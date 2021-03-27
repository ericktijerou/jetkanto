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
import com.ericktijerou.jetkanto.data.local.RecordDataStore
import com.ericktijerou.jetkanto.domain.entity.Record
import com.ericktijerou.jetkanto.domain.repository.RecordRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RecordRepositoryImpl @Inject constructor(
    private val recordDataStore: RecordDataStore
) : RecordRepository {
    override fun getRecordList(): Flow<List<Record>> {
        return recordDataStore.getRecordList().map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun setFavoriteRecord(idRecord: Long, isFavorite: Boolean) {
        return recordDataStore.setFavoriteRecord(idRecord, isFavorite)
    }
}
