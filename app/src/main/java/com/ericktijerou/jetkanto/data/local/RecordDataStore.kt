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
package com.ericktijerou.jetkanto.data.local

import com.ericktijerou.jetkanto.data.entity.RecordModel
import com.ericktijerou.jetkanto.data.entity.toLocal
import com.ericktijerou.jetkanto.data.local.dao.RecordDao
import com.ericktijerou.jetkanto.data.local.entity.toData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RecordDataStore @Inject constructor(
    private val recordDao: RecordDao
) {
    fun getRecordList(): Flow<List<RecordModel>> {
        return recordDao.getAll().map { list ->
            list.map { it.toData() }
        }
    }

    suspend fun saveRecordList(list: List<RecordModel>) {
        recordDao.clearAll()
        recordDao.insertRecords(*list.map { it.toLocal() }.toTypedArray())
    }

    suspend fun clearAll() {
        return recordDao.clearAll()
    }
}
