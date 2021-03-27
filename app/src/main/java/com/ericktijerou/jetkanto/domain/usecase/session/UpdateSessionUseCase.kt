package com.ericktijerou.jetkanto.domain.usecase.session

import com.ericktijerou.jetkanto.domain.entity.User
import com.ericktijerou.jetkanto.domain.repository.SessionRepository
import javax.inject.Inject

class UpdateSessionUseCase @Inject constructor(private val repository: SessionRepository) {
    suspend operator fun invoke(user: User) {
        repository.updateSession(user)
    }
}