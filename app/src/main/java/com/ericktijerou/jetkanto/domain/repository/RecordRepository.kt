package com.ericktijerou.jetkanto.domain.repository

import com.ericktijerou.jetkanto.domain.entity.Record
import kotlinx.coroutines.flow.Flow

interface RecordRepository {
    fun getRecordList(): Flow<List<Record>>
}