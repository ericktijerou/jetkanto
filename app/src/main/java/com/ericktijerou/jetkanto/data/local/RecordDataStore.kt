package com.ericktijerou.jetkanto.data.local

import androidx.paging.PagingSource
import com.ericktijerou.jetkanto.data.entity.RecordModel
import com.ericktijerou.jetkanto.data.entity.toLocal
import com.ericktijerou.jetkanto.data.local.dao.RecordDao
import com.ericktijerou.jetkanto.data.local.entity.RecordEntity
import javax.inject.Inject

class RecordDataStore @Inject constructor(
    private val recordDao: RecordDao
) {
    fun getRecordList(): PagingSource<Int, RecordEntity> {
        return recordDao.getAll()
    }

    suspend fun saveRecordList(list: List<RecordModel>) {
        recordDao.insertRecords(*list.map { it.toLocal() }.toTypedArray())
    }

    suspend fun clearAll() {
        return recordDao.clearAll()
    }
}