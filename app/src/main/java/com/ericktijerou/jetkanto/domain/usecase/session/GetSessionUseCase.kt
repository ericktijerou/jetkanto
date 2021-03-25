package com.ericktijerou.jetkanto.domain.usecase.session

import com.ericktijerou.jetkanto.domain.repository.SessionRepository
import javax.inject.Inject

class GetSessionUseCase @Inject constructor(private val repository: SessionRepository) {
    operator fun invoke() = repository.getSession()
}