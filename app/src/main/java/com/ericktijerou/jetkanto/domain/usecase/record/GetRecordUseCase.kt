package com.ericktijerou.jetkanto.domain.usecase.record

import com.ericktijerou.jetkanto.domain.repository.RecordRepository
import javax.inject.Inject

class GetRecordUseCase @Inject constructor(private val repository: RecordRepository) {
    operator fun invoke() = repository.getRecordList()
}