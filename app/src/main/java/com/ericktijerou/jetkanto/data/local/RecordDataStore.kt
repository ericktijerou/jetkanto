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
        recordDao.insertRecords(*list.map { it.toLocal() }.toTypedArray())
    }

    suspend fun clearAll() {
        return recordDao.clearAll()
    }
}