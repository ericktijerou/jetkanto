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
package com.ericktijerou.jetkanto.ui.editprofile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ericktijerou.jetkanto.domain.usecase.session.GetSessionUseCase
import com.ericktijerou.jetkanto.domain.usecase.session.UpdateSessionUseCase
import com.ericktijerou.jetkanto.ui.entity.UserView
import com.ericktijerou.jetkanto.ui.entity.toDomain
import com.ericktijerou.jetkanto.ui.entity.toView
import com.ericktijerou.jetkanto.ui.util.ViewState
import com.ericktijerou.jetkanto.ui.util.shareWhileObserved
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    getSessionUseCase: GetSessionUseCase,
    private val updateSessionUseCase: UpdateSessionUseCase
) : ViewModel() {

    private var job: Job? = null

    private val _updateSessionState = MutableSharedFlow<ViewState>()
    val updateSessionState = _updateSessionState.shareWhileObserved(viewModelScope)

    val session: SharedFlow<UserView> = getSessionUseCase.invoke()
        .map { it.toView().apply { username = username.takeLast(username.length - 1) } }
        .flowOn(Dispatchers.IO)
        .shareWhileObserved(viewModelScope)

    fun updateSession(userView: UserView) {
        job?.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            _updateSessionState.emit(ViewState.Loading)
            updateSessionUseCase.invoke(userView.apply { username = "@$username" }.toDomain())
            _updateSessionState.emit(ViewState.Success)
        }
    }
}
