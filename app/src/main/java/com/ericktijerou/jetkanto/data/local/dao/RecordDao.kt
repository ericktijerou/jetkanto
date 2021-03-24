package com.ericktijerou.jetkanto.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ericktijerou.jetkanto.data.local.entity.RecordEntity

@Dao
interface RecordDao {
    @Query("SELECT * FROM Record")
    fun getAll(): PagingSource<Int, RecordEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecords(vararg repos: RecordEntity)

    @Query("DELETE FROM Record")
    suspend fun clearAll()
}
