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
}