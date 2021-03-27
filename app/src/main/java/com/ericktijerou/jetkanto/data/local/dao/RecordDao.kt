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
package com.ericktijerou.jetkanto.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ericktijerou.jetkanto.data.local.entity.RecordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecordDao {
    @Query("SELECT * FROM Record")
    fun getAll(): Flow<List<RecordEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecords(vararg repos: RecordEntity)

    @Query("DELETE FROM Record")
    suspend fun clearAll()

    @Query("SELECT * FROM Record WHERE id = :id ")
    suspend fun getRecordById(id: Long): RecordEntity

    @Query("UPDATE Record SET isFavorite = :value, likeCount = :likeCount WHERE id LIKE :recordId")
    suspend fun updateFavoriteById(recordId: Long, value: Boolean, likeCount: Int)
}
